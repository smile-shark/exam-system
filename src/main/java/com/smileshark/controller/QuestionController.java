package com.smileshark.controller;

import com.smileshark.common.RequestParams;
import com.smileshark.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    @RequestMapping("/teacher/question/count")
    public String count(){
        return questionService.questionCount();
    }
    @RequestMapping("/teacher/question/list/byCourseChapterSubsectionContentType")
    public String questionListByCourseChapterSubsectionContentType(@RequestBody RequestParams requestParams){
        return questionService.questionListByCourseChapterSubsectionContentType(requestParams);
    }
    @RequestMapping("/teacher/question/select/bySubsectionsAndTypeLimitNum")
    public String selectQuestionBySubsectionsAndTypeLimitNum(@RequestBody RequestParams requestParams){
        return questionService.selectQuestionBySubsectionsAndTypeLimitNum(requestParams);
    }
    @RequestMapping("/teacher/question/select/bySubsectionsAndTypeLimitNumNotQuestionId")
    public String selectQuestionBySubsectionsAndTypeLimitNumNotQuestionId(@RequestBody RequestParams requestParams){
        return questionService.selectQuestionBySubsectionsAndTypeLimitNumNotQuestionId(requestParams);
    }
    @RequestMapping("/teacher/insert/questions/insertQuestion")
    public String insertQuestion(@RequestBody RequestParams requestParams){
        return questionService.insertQuestion(requestParams);
    }
}
