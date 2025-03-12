package com.smileshark.entity.question;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Chapter {
    private String chapterId;
    private String courseId;
    private Course course;
    private String chapterTitle;
    private String chapterName;
    private Integer order;
}
