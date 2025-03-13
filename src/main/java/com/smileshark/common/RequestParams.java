package com.smileshark.common;

import lombok.Data;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class RequestParams {
    private String studentName;
    private String studentAccount;
    private String studentPassword;
    private String administratorId;
    private String administratorAccount;
    private String administratorPassword;
    private String courseId;
    private String chapterId;
    private String subsectionId;
    private String questionContent;
    private Boolean vague;
    private List<Integer> questionTypes;
    private Integer page;
    private Integer size;
    private String questionId;
}
