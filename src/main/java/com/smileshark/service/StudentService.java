package com.smileshark.service;

import com.smileshark.common.RequestParams;

public interface StudentService {
    String studentSignup(RequestParams requestParams);
    String studentLogin(RequestParams requestParams);
    String studentCount();
    String selectAllStudent();
    String selectStudentInStudentIds(RequestParams requestParams);
}
