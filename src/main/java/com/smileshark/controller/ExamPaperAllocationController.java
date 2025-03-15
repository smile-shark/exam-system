package com.smileshark.controller;

import com.smileshark.common.RequestParams;
import com.smileshark.service.ExamPaperAllocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExamPaperAllocationController {
    private final ExamPaperAllocationService examPaperAllocationService;
    @RequestMapping("/student/count/allocationExamPaperCountByStudentIdAndState")
    public String allocationExamPaperCountByStudentIdAndState(@RequestBody RequestParams requestParams) {
        return examPaperAllocationService.allocationExamPaperCountByStudentIdAndState(requestParams);
    }
    @RequestMapping("/student/count/ExamCountByStudentId")
    public String examCountByStudentId(@RequestBody RequestParams requestParams) {
        return examPaperAllocationService.ExamCountByStudentId(requestParams);
    }
    @RequestMapping("/student/list/selectExamPaperAllocationsByStudentIdParamState")
    public String selectExamPaperAllocationsByStudentIdParamState(@RequestBody RequestParams requestParams) {
        return examPaperAllocationService.selectExamPaperAllocationsByStudentIdParamState(requestParams);
    }
    @RequestMapping("/student/exam/selectExamPaperAllocationByExamPaperAllocationId")
    public String selectExamPaperAllocationByExamPaperAllocationId(@RequestBody RequestParams requestParams) {
        return examPaperAllocationService.selectExamPaperAllocationByExamPaperAllocationId(requestParams);
    }
}
