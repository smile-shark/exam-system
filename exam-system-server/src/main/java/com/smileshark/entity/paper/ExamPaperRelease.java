package com.smileshark.entity.paper;

import com.smileshark.entity.user.Administrator;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ExamPaperRelease {
    private String examPaperReleaseId;
    private String examPaperId;
    private ExamPaper examPaper;
    private String AdministratorId;
    private Administrator administrator;
    private Long examPaperReleaseTime;
    private Long examStartTime;
    private Long examEndTime;
    private String notes;
    private Long duration;
}
