package com.smileshark.controller;

import com.smileshark.common.RequestParams;
import com.smileshark.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    @RequestMapping("/student/signup")
    public String signup(@RequestBody RequestParams requestParams){
        return studentService.studentSignup(requestParams);
    }
    @RequestMapping("/student/login")
    public String login(@RequestBody RequestParams requestParams){
        return studentService.studentLogin(requestParams);
    }
    @RequestMapping("/teacher/student/count")
    public String count(){
        return studentService.studentCount();
    }
}
