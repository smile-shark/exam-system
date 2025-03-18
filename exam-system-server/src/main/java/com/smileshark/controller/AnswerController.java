package com.smileshark.controller;

import com.smileshark.common.RequestParams;
import com.smileshark.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;
    @RequestMapping("/teacher/answer/info/by/questionId")
    public String selectAnswerByQuestionId(@RequestBody RequestParams requestParams){
        return answerService.selectAnswerByQuestionId(requestParams);
    }
}
