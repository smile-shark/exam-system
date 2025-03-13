package com.smileshark.entity.question;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Course {
    private String courseId;
    private String courseName;
    private String courseImage;
    private String courseDescribe;
}
