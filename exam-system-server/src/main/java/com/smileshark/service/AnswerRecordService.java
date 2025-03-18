package com.smileshark.service;

import com.smileshark.common.RequestParams;

public interface AnswerRecordService {
    String answerRecordCountByStudentId(RequestParams requestParams);
    String wrongQuestionRecordCountByStudentId(RequestParams requestParams);
    String insertAnswerRecord(RequestParams requestParams);
    String selectAnswerRecordByStudentIdIsFalse(RequestParams requestParams);
}
