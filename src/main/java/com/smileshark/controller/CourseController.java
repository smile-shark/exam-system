package com.smileshark.controller;

import com.smileshark.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    @RequestMapping("/teacher/course/count")
    public String count(){
        return courseService.courseCount();
    }
    @RequestMapping("/teacher/course/list/name/id")
    public String selectAllCoursesNameAndId(){
        return courseService.selectAllCoursesNameAndId();
    }
}
