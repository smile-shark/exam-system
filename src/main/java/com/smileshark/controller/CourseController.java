package com.smileshark.controller;

import com.smileshark.common.RequestParams;
import com.smileshark.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
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
    @RequestMapping("/teacher/course/list/selectAllCoursesAndChapters")
    public String selectAllCoursesAndChapters(@RequestBody RequestParams requestParams){
        return courseService.selectAllCoursesAndChapters(requestParams);
    }
    @RequestMapping("/teacher/course/insert/insertCourse")
    public String insertCourse(@RequestBody RequestParams requestParams){
        return courseService.insertCourse(requestParams);
    }
    @RequestMapping("/teacher/course/update/updateCourse")
    public String updateCourse(@RequestBody RequestParams requestParams){
        return courseService.updateCourse(requestParams);
    }
    @RequestMapping("/teacher/course/delete/deleteCourseByCourseId")
    public String deleteCourseByCourseId(@RequestBody RequestParams requestParams){
        return courseService.deleteCourseByCourseId(requestParams);
    }
}
