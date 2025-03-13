package com.smileshark.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smileshark.common.RequestParams;
import com.smileshark.common.Result;
import com.smileshark.entity.question.Chapter;
import com.smileshark.entity.question.Question;
import com.smileshark.entity.question.Subsection;
import com.smileshark.mapper.ChapterMapper;
import com.smileshark.mapper.QuestionMapper;
import com.smileshark.mapper.SubsectionMapper;
import com.smileshark.service.QuestionService;
import com.smileshark.utils.VagueUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
public class QuestionServiceImp implements QuestionService {
    private final QuestionMapper questionMapper;
    private final ChapterMapper chapterMapper;
    private final SubsectionMapper subsectionMapper;
    @Override
    public String questionCount() {
        return JSONObject.toJSONString(Result.success("获取成功",questionMapper.questionCount()));
    }

    @Override
    public String questionListByCourseChapterSubsectionContentType(RequestParams requestParams) {
        Result result = Result.error();
        if(requestParams.getVague()){
            // 替换位模糊字符串
            requestParams.setQuestionContent(VagueUtil.createVagueString(
                    requestParams.getQuestionContent()
            ));
        }
        if(requestParams.getSubsectionId()!=null&& !requestParams.getSubsectionId().isEmpty()){
            // 根据单一的小节获取题目列表
            Page<Question> page = PageHelper.startPage(requestParams.getPage(), requestParams.getSize());
            List<Question> questions = questionMapper.selectQuestionByParamsAndSubsectionId(
                    requestParams.getSubsectionId(),
                    requestParams.getQuestionContent(),
                    requestParams.getQuestionTypes());
            result = Result.success("获取成功", PageInfo.of(page));
        }else if(requestParams.getChapterId()!=null&& !requestParams.getChapterId().isEmpty()){
            // 根据章节获取小节
            List<Subsection> subsections = subsectionMapper.selectAllSubsectionsIdByChapterId(requestParams.getChapterId());
            Page<Question> page = PageHelper.startPage(requestParams.getPage(), requestParams.getSize());
            List<Question> questions = questionMapper.selectQuestionByParamsInSubsectionIds(
                    subsections,
                    requestParams.getQuestionContent(),
                    requestParams.getQuestionTypes()
            );
            result = Result.success("获取成功",PageInfo.of(page));
        }else if(requestParams.getCourseId()!=null && !requestParams.getCourseId().isEmpty()){
            // 根据课程获取章节,然后获取小节
            List<Subsection> subsections= new CopyOnWriteArrayList<>();
            for (Chapter chapter : chapterMapper.selectAllChaptersIdByCourseId(requestParams.getCourseId())) {
                subsections.addAll(subsectionMapper.selectAllSubsectionsIdByChapterId(chapter.getChapterId()));
            }
            Page<Question> page = PageHelper.startPage(requestParams.getPage(), requestParams.getSize());
            List<Question> questions = questionMapper.selectQuestionByParamsInSubsectionIds(
                    subsections,
                    requestParams.getQuestionContent(),
                    requestParams.getQuestionTypes()
            );
            result = Result.success("获取成功",PageInfo.of(page));
        }else{
            // 根据参数获取
            Page<Question> page = PageHelper.startPage(requestParams.getPage(), requestParams.getSize());
            List<Question> questions = questionMapper.selectQuestionByParams(requestParams.getQuestionContent(), requestParams.getQuestionTypes());
            result = Result.success("获取成功",PageInfo.of(page));
        }
        return JSONObject.toJSONString(result);
    }
}
