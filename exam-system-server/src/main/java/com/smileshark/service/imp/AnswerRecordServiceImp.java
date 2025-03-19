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
import com.smileshark.entity.question.Answer;
import com.smileshark.entity.question.Question;
import com.smileshark.entity.score.AnswerRecord;
import com.smileshark.entity.score.Score;
import com.smileshark.entity.score.StudentSelectedAnswer;
import com.smileshark.mapper.*;
import com.smileshark.service.AnswerRecordService;
import com.smileshark.utils.CreateId;
import com.smileshark.utils.SelectedIsTrueOrFalse;
import com.smileshark.utils.ThreadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnswerRecordServiceImp implements AnswerRecordService {
    private final AnswerRecordMapper answerRecordMapper;
    private final StudentSelectedAnswerMapper studentSelectedAnswerMapper;
    private final ExamPaperAllocationMapper examPaperAllocationMapper;
    private final ScoreMapper scoreMapper;
    private final QuestionMapper questionMapper;
    private final ExamPaperMapper examPaperMapper;
    private int isTrueCount = 0;

    public void addIsTrueCount() {
        synchronized (this) {
            this.isTrueCount++;
        }
    }

    @Override
    public String answerRecordCountByStudentId(RequestParams requestParams) {
        return JSONObject.toJSONString(
                Result.success(null,
                        answerRecordMapper.answerRecordCountByStudentId(requestParams.getStudentId()))
                , SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String wrongQuestionRecordCountByStudentId(RequestParams requestParams) {
        return JSONObject.toJSONString(
                Result.success(null,
                        answerRecordMapper.wrongQuestionRecordCountByStudentId(requestParams.getStudentId()))
                , SerializerFeature.DisableCircularReferenceDetect );
    }

    @Override
    public String insertAnswerRecord(RequestParams requestParams) {
        Result result = Result.error();
        if (examPaperAllocationMapper.updateExamPaperAllocationStateToFinish(
                requestParams.getExamPaperAllocation().getExamPaperAllocationId()
        ) > 0) {
            for (QuestionOnTestPaper questionOnTestPaper : requestParams
                    .getExamPaperAllocation()
                    .getExamPaperRelease()
                    .getExamPaper()
                    .getQuestionOnTestPapers()) {
                Question question = questionMapper.selectQuestionByQuestionIdHaveReallyAnswer(
                        questionOnTestPaper.getQuestion().getQuestionId()
                );
                String answerRecordId = CreateId.createId();
                AnswerRecord answerRecord = new AnswerRecord() {{
                    setExamPaperAllocationId(
                            requestParams.getExamPaperAllocation().getExamPaperAllocationId()
                    );
                    setQuestionId(
                            question.getQuestionId()
                    );
                    setStudentId(
                            requestParams.getExamPaperAllocation().getStudentId()
                    );
                    setAnswerRecordId(answerRecordId);
                }};
                List<String> answerIds = question.getAnswers().stream()
                        .map(Answer::getAnswerId)
                        .toList();
                boolean isTrue = false;
                if (questionOnTestPaper.getQuestion().getSelectedId() != null && !questionOnTestPaper.getQuestion().getSelectedId().isEmpty()) {
                    // 如果是单选题
                    if (SelectedIsTrueOrFalse.isTrueOrFalse(
                            List.of(questionOnTestPaper.getQuestion().getSelectedId()),
                            answerIds
                    )) {
                        isTrue = true;
                    }
                    answerRecord.setIsTrue(isTrue);
                    answerRecordMapper.insertAnswerRecord(answerRecord);
                    studentSelectedAnswerMapper.insertStudentSelectedAnswer(new StudentSelectedAnswer() {{
                        setAnswerId(questionOnTestPaper.getQuestion().getSelectedId());
                        setAnswerRecordId(answerRecordId);
                    }});
                } else if (!questionOnTestPaper.getQuestion().getSelectedIds().isEmpty()) {
                    // 如果是多选题
                    if (SelectedIsTrueOrFalse.isTrueOrFalse(
                            questionOnTestPaper.getQuestion().getSelectedIds(),
                            answerIds
                    )) {
                        isTrue = true;
                    }

                    answerRecord.setIsTrue(isTrue);
                    answerRecordMapper.insertAnswerRecord(answerRecord);
                    for (String selectedId : questionOnTestPaper.getQuestion().getSelectedIds()) {
                        studentSelectedAnswerMapper.insertStudentSelectedAnswer(new StudentSelectedAnswer() {{
                            setAnswerId(selectedId);
                            setAnswerRecordId(answerRecordId);
                        }});
                    }
                }
                if (isTrue) {
                    this.addIsTrueCount();
                }
            }
            ExamPaper examPaper = examPaperMapper.selectExamPaperByExamPaperId(
                    requestParams
                            .getExamPaperAllocation()
                            .getExamPaperRelease()
                            .getExamPaper()
                            .getExamPaperId()
            );
            // 计算分数
            double score = (double) this.isTrueCount / examPaper.getQuestionCount() * examPaper.getTotalScore();
            // 将数据插入到score表中
            if (scoreMapper.insertScore(new Score() {{
                setStudentId(requestParams.getExamPaperAllocation().getStudentId());
                setExamPaperReleaseId(requestParams.getExamPaperAllocation().getExamPaperRelease().getExamPaperReleaseId());
                setExamPaperAllocationId(requestParams.getExamPaperAllocation().getExamPaperAllocationId());
                setScore(score);
                setFinishTime(System.currentTimeMillis());
            }}) > 0) {
                result = Result.success();
                result.setMsg("提交成功");
                result.setData(score);
            }
        } else {
            result.setMsg("你已经提交过答卷了，不能再提交");
        }
        return JSONObject.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String selectAnswerRecordByStudentIdIsFalse(RequestParams requestParams) {
        Page<Object> objects = PageHelper.startPage(requestParams.getPage(), requestParams.getSize());
        List<AnswerRecord> answerRecords = answerRecordMapper.selectAnswerRecordByStudentIdIsFalse(requestParams.getStudentId());
        return JSONObject.toJSONString(
                Result.success(null, PageInfo.of(objects))
                , SerializerFeature.DisableCircularReferenceDetect);
    }
}
