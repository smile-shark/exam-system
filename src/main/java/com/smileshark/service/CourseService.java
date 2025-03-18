package com.smileshark.service;

import com.smileshark.common.RequestParams;

public interface CourseService {
    String courseCount();
    String selectAllCoursesNameAndId();
    String selectAllCoursesAndChapters(RequestParams requestParams);
    String insertCourse(RequestParams requestParams);
    String updateCourse(RequestParams requestParams);
    String deleteCourseByCourseId(RequestParams requestParams);
}
