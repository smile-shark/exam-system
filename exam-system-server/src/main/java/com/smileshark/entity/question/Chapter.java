package com.smileshark.entity.question;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class Chapter {
    private String chapterId;
    private String courseId;
    private Course course;
    private String chapterTitle;
    private String chapterName;
    private Integer order;
    private List<Subsection> subsections;
}
