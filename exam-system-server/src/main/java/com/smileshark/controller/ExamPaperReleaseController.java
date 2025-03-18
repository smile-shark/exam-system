package com.smileshark.controller;

import com.smileshark.common.RequestParams;
import com.smileshark.service.ExamPaperReleaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExamPaperReleaseController {
    private final ExamPaperReleaseService examPaperReleaseService;
    @RequestMapping("/teacher/release/examPaper/insertExamPaperRelease")
    public String insertExamPaperRelease(@RequestBody RequestParams requestParams) {
        return examPaperReleaseService.insertExamPaperRelease(requestParams);
    }
}
