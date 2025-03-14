package com.smileshark.service;

import com.smileshark.common.RequestParams;

public interface ScoreService {
    String lastScoreByExamPaperAllocationId(RequestParams requestParams);
    String avgScoreByStudentId(RequestParams requestParams);
}
