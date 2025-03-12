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
    private Integer examPaperReleaseTime;
    private Integer examStartTime;
    private Integer examEndTime;
    private String notes;
}
