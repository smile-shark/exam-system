package com.smileshark.controller;

import com.smileshark.common.RequestParams;
import com.smileshark.service.AnswerRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AnswerRecordController {
    private final AnswerRecordService answerRecordService;
    @RequestMapping("/student/answerRecordCountByStudentId")
    public String answerRecordCountByStudentId(@RequestBody RequestParams requestParams) {
        return answerRecordService.answerRecordCountByStudentId(requestParams);
    }
    @RequestMapping("/student/wrongQuestionRecordCountByStudentId")
    public String wrongQuestionRecordCountByStudentId(@RequestBody RequestParams requestParams) {
        return answerRecordService.wrongQuestionRecordCountByStudentId(requestParams);
    }
}
