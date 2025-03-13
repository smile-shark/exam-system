package com.smileshark.controller;

import com.smileshark.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    @RequestMapping("/question/count")
    public String count(){
        return questionService.questionCount();
    }
}
