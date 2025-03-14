package com.smileshark.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.smileshark.common.RequestParams;
import com.smileshark.common.Result;
import com.smileshark.mapper.AnswerRecordMapper;
import com.smileshark.service.AnswerRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerRecordServiceImp implements AnswerRecordService {
    private final AnswerRecordMapper answerRecordMapper;
    @Override
    public String answerRecordCountByStudentId(RequestParams requestParams) {
        return JSONObject.toJSONString(
                Result.success(null,
                        answerRecordMapper.answerRecordCountByStudentId(requestParams.getStudentId()))
        );
    }

    @Override
    public String wrongQuestionRecordCountByStudentId(RequestParams requestParams) {
        return JSONObject.toJSONString(
                Result.success(null,
                        answerRecordMapper.wrongQuestionRecordCountByStudentId(requestParams.getStudentId()))
        );
    }
}
