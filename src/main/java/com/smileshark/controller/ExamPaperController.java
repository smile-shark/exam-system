package com.smileshark.controller;

import com.smileshark.service.ExamPaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExamPaperController {
    private final ExamPaperService examPaperService;
    @RequestMapping("/exam/paper/count")
    public String count(){
        return examPaperService.examPaperCount();
    }
}
