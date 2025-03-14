package com.smileshark.controller;

import com.smileshark.common.RequestParams;
import com.smileshark.service.ExamPaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExamPaperController {
    private final ExamPaperService examPaperService;
    @RequestMapping("/teacher/exam/paper/count")
    public String count(){
        return examPaperService.examPaperCount();
    }
    @RequestMapping("/teacher/exam/paper/insert")
    public String insertExamPaper(@RequestBody RequestParams requestParams){
        return examPaperService.insertExamPaper(requestParams);
    }
    @RequestMapping("/teacher/exam/paper/select/selectAllExamPaperIdTitle")
    public String selectAllExamPaperIdTitle(){
        return examPaperService.selectAllExamPaperIdTitle();
    }
    @RequestMapping("/teacher/exam/paper/select/selectExamPaperByParams")
    public String selectExamPaperByParams(@RequestBody RequestParams requestParams){
        return examPaperService.selectExamPaperByParams(requestParams);
    }
}
