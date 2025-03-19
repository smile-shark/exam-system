package com.smileshark.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smileshark.common.RequestParams;
import com.smileshark.common.Result;
import com.smileshark.entity.paper.ExamPaper;
import com.smileshark.entity.paper.QuestionOnTestPaper;
import com.smileshark.entity.question.Question;
import com.smileshark.mapper.ExamPaperMapper;
import com.smileshark.mapper.QuestionOnTestPaperMapper;
import com.smileshark.service.ExamPaperService;
import com.smileshark.utils.CreateId;
import com.smileshark.utils.VagueUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamPaperServiceImp implements ExamPaperService {
    private final ExamPaperMapper examPaperMapper;
    private final QuestionOnTestPaperMapper questionOnTestPaperMapper;

    @Override
    public String examPaperCount() {
        return JSONObject.toJSONString(Result.success(null,examPaperMapper.examPaperCount()));
    }

    @Override
    public String insertExamPaper(RequestParams requestParams) {
        Result result = Result.error();
        // 先判断是否有相同标题的试卷
        if(!examPaperMapper.selectExamPaperByTitle(requestParams.getExamPaperTitle()).isEmpty()){
            result.setMsg("试卷标题重复");
            return JSONObject.toJSONString(result);
        }
        // 先创建试卷
        String examPaperId = CreateId.createId();
        long examPaperCreateTime = System.currentTimeMillis();
        int i = examPaperMapper.insertExamPaper(new ExamPaper() {{
            setExamPaperId(examPaperId);
            setAdministratorId(requestParams.getAdministratorId());
            setExamPaperTitle(requestParams.getExamPaperTitle());
            setExamPaperCreateTime(examPaperCreateTime);
            setQuestionCount(requestParams.getQuestions().size());
            setTotalScore(requestParams.getTotalScore());    // 总分
        }});
        if (i > 0) {
            // 再创建试卷题目
            for (Question question : requestParams.getQuestions()) {
             questionOnTestPaperMapper.insertItem(new QuestionOnTestPaper(){{
                 setQuestionId(question.getQuestionId());
                 setExamPaperId(examPaperId);
             }});
            }
        }
        return JSONObject.toJSONString(Result.success("创建成功",examPaperId));
    }

    @Override
    public String selectAllExamPaperIdTitle() {
        return JSONObject.toJSONString(Result.success("获取成功",
                examPaperMapper.selectAllExamPaperIdTitle())
                , SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String selectExamPaperByParams(RequestParams requestParams) {
        if (requestParams.getVague()){
            requestParams.setExamPaperTitle(
                    VagueUtil.createVagueString(requestParams.getExamPaperTitle())
            );
        }
        Page<ExamPaper> page = PageHelper.startPage(requestParams.getPage(), requestParams.getSize());
        List<ExamPaper> examPapers = examPaperMapper.selectExamPaperByParams(
                requestParams.getAdministratorIds(),
                requestParams.getExamPaperStates(),
                requestParams.getExamPaperTitle()
        );
        return JSONObject.toJSONString(Result.success("获取成功", PageInfo.of(page))
                , SerializerFeature.DisableCircularReferenceDetect);
    }
}
