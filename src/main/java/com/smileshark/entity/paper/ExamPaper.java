package com.smileshark.entity.paper;

import com.smileshark.entity.user.Administrator;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ExamPaper {
    private String examPaperId;
    private String administratorId;
    private Administrator administrator;
    private String examPaperTitle;
    private Long examPaperCreateTime;
    private Integer questionCount;
    private Integer examPaperState;
    private Double totalScore;
}
