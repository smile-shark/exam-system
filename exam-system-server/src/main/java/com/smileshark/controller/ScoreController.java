package com.smileshark.controller;

import com.smileshark.common.RequestParams;
import com.smileshark.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScoreController {
    private final ScoreService scoreService;
    @RequestMapping("/student/lastScoreByExamPaperAllocationId")
    public String lastScoreByExamPaperAllocationId(@RequestBody RequestParams requestParams) {
        return scoreService.lastScoreByExamPaperAllocationId(requestParams);
    }
    @RequestMapping("/student/avgScoreByStudentId")
    public String avgScoreByStudentId(@RequestBody RequestParams requestParams) {
        return scoreService.avgScoreByStudentId(requestParams);
    }
}
