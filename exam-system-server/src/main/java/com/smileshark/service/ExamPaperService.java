package com.smileshark.service;

import com.smileshark.common.RequestParams;

public interface ExamPaperService {
    String examPaperCount();
    String insertExamPaper(RequestParams requestParams);
    String selectAllExamPaperIdTitle();
    String selectExamPaperByParams(RequestParams requestParams);
    String updateExamPaperToCancel(RequestParams requestParams);
    String deleteExamPaperById(RequestParams requestParams);
}
