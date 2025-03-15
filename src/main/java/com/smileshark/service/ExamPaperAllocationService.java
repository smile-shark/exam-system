package com.smileshark.service;

import com.smileshark.common.RequestParams;

public interface ExamPaperAllocationService {
    String allocationExamPaperCountByStudentIdAndState(RequestParams requestParams);
    String ExamCountByStudentId(RequestParams requestParams);
    String selectExamPaperAllocationsByStudentIdParamState(RequestParams requestParams);
    String selectExamPaperAllocationByExamPaperAllocationId(RequestParams requestParams);
}
