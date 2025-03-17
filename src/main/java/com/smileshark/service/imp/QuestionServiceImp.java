package com.smileshark.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smileshark.common.RequestParams;
import com.smileshark.common.Result;
import com.smileshark.entity.question.Answer;
import com.smileshark.entity.question.Chapter;
import com.smileshark.entity.question.Question;
import com.smileshark.entity.question.Subsection;
import com.smileshark.mapper.AnswerMapper;
import com.smileshark.mapper.ChapterMapper;
import com.smileshark.mapper.QuestionMapper;
import com.smileshark.mapper.SubsectionMapper;
import com.smileshark.service.QuestionService;
import com.smileshark.utils.CreateId;
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
    private final AnswerMapper answerMapper;

    @Override
    public String questionCount() {
        return JSONObject.toJSONString(Result.success("获取成功", questionMapper.questionCount()));
    }

    @Override
    public String questionListByCourseChapterSubsectionContentType(RequestParams requestParams) {
        Result result;
        if (requestParams.getVague()) {
            // 替换位模糊字符串
            requestParams.setQuestionContent(VagueUtil.createVagueString(
                    requestParams.getQuestionContent()
            ));
        }
        if (requestParams.getSubsectionId() != null && !requestParams.getSubsectionId().isEmpty()) {
            // 根据单一的小节获取题目列表
            Page<Question> page = PageHelper.startPage(requestParams.getPage(), requestParams.getSize());
            List<Question> questions = questionMapper.selectQuestionByParamsAndSubsectionId(
                    requestParams.getSubsectionId(),
                    requestParams.getQuestionContent(),
                    requestParams.getQuestionTypes());
            result = Result.success("获取成功", PageInfo.of(page));
        } else if (requestParams.getChapterId() != null && !requestParams.getChapterId().isEmpty()) {
            // 根据章节获取小节
            List<Subsection> subsections = subsectionMapper.selectAllSubsectionsIdByChapterId(requestParams.getChapterId());
            Page<Question> page = PageHelper.startPage(requestParams.getPage(), requestParams.getSize());
            List<Question> questions = questionMapper.selectQuestionByParamsInSubsectionIds(
                    subsections,
                    requestParams.getQuestionContent(),
                    requestParams.getQuestionTypes()
            );
            result = Result.success("获取成功", PageInfo.of(page));
        } else if (requestParams.getCourseId() != null && !requestParams.getCourseId().isEmpty()) {
            // 根据课程获取章节,然后获取小节
            List<Subsection> subsections = new CopyOnWriteArrayList<>();
            for (Chapter chapter : chapterMapper.selectAllChaptersIdByCourseId(requestParams.getCourseId())) {
                subsections.addAll(subsectionMapper.selectAllSubsectionsIdByChapterId(chapter.getChapterId()));
            }
            Page<Question> page = PageHelper.startPage(requestParams.getPage(), requestParams.getSize());
            List<Question> questions = questionMapper.selectQuestionByParamsInSubsectionIds(
                    subsections,
                    requestParams.getQuestionContent(),
                    requestParams.getQuestionTypes()
            );
            result = Result.success("获取成功", PageInfo.of(page));
        } else {
            // 根据参数获取
            Page<Question> page = PageHelper.startPage(requestParams.getPage(), requestParams.getSize());
            List<Question> questions = questionMapper.selectQuestionByParams(requestParams.getQuestionContent(), requestParams.getQuestionTypes());
            result = Result.success("获取成功", PageInfo.of(page));
        }
        return JSONObject.toJSONString(result);
    }

    @Override
    public String selectQuestionBySubsectionsAndTypeLimitNum(RequestParams requestParams) {
        List<Question> questions = new CopyOnWriteArrayList<>();
        List<Subsection> subsections = getSubsections(requestParams);
        if (requestParams.getQuestionType0Count() > 0) {
            if (subsections.isEmpty()) {
                questions.addAll(questionMapper.selectQuestionByTypeLimitNum(
                        0,
                        requestParams.getQuestionType0Count()
                ));
            } else {
                questions.addAll(questionMapper.selectQuestionBySubsectionsAndTypeLimitNum(
                        subsections,
                        0,
                        requestParams.getQuestionType0Count()
                ));
            }
        }
        if (requestParams.getQuestionType1Count() > 0) {
            if (subsections.isEmpty()) {
                questions.addAll(questionMapper.selectQuestionByTypeLimitNum(
                        1,
                        requestParams.getQuestionType1Count()
                ));
            } else {
                questions.addAll(questionMapper.selectQuestionBySubsectionsAndTypeLimitNum(
                        subsections,
                        1,
                        requestParams.getQuestionType1Count()
                ));
            }
        }
        if (requestParams.getQuestionType2Count() > 0) {
            if (subsections.isEmpty()) {
                questions.addAll(questionMapper.selectQuestionByTypeLimitNum(
                        2,
                        requestParams.getQuestionType2Count()
                ));
            } else {
                questions.addAll(questionMapper.selectQuestionBySubsectionsAndTypeLimitNum(
                        subsections,
                        2,
                        requestParams.getQuestionType2Count()
                ));
            }
        }
        return JSONObject.toJSONString(Result.success("获取成功", questions));
    }

    @Override
    public String selectQuestionBySubsectionsAndTypeLimitNumNotQuestionId(RequestParams requestParams) {
        Question question;
        List<Subsection> subsections = getSubsections(requestParams);
        if (subsections.isEmpty()) {
            question = questionMapper.selectQuestionByTypeLimitNumNotQuestionId(
                    requestParams.getQuestionType(),
                    requestParams.getQuestionId()
            );
        } else {
            question = questionMapper.selectQuestionBySubsectionsAndTypeLimitNumNotQuestionId(
                    subsections,
                    requestParams.getQuestionType(),
                    requestParams.getQuestionId()
            );
        }
        return JSONObject.toJSONString(Result.success("获取成功", question));
    }

    @Override
    public String insertQuestion(RequestParams requestParams) {
        // 先循环插入，再循环插入答案
        List<Question> questions = requestParams.getQuestions();
        for (Question question : questions) {
            String questionId = CreateId.createId();
            question.setQuestionId(questionId);
            question.setSubsectionId(requestParams.getSubsectionId());
            for (Answer answer : question.getAnswers()) {
                answer.setQuestionId(questionId);
                answer.setAnswerId(CreateId.createId());
            }
        }
        // 插入题目
        if (questionMapper.insertQuestion(questions) > 0) {
            answerMapper.insertAnswer(questions.stream().flatMap(question -> question.getAnswers().stream()).toList());
        }
        return JSONObject.toJSONString(Result.success("插入成功"));
    }

    private List<Subsection> getSubsections(RequestParams requestParams) {
        List<Subsection> subsections = new CopyOnWriteArrayList<>();
        if (!requestParams.getSubsectionIds().isEmpty()) {
            for (String subsectionId : requestParams.getSubsectionIds()) {
                subsections.add(new Subsection() {{
                    setSubsectionId(subsectionId);
                }});
            }
        } else if (!requestParams.getChapterIds().isEmpty()) {
            for (String chapterId : requestParams.getChapterIds()) {
                subsections.addAll(subsectionMapper.selectAllSubsectionsIdByChapterId(chapterId));
            }
        } else if (!requestParams.getCourseIds().isEmpty()) {
            for (String courseId : requestParams.getCourseIds()) {
                for (Chapter chapter : chapterMapper.selectAllChaptersIdByCourseId(courseId)) {
                    subsections.addAll(subsectionMapper.selectAllSubsectionsIdByChapterId(chapter.getChapterId()));
                }
            }
        }
        return subsections;
    }
}
