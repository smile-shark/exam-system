package com.smileshark.entity.score;

import com.smileshark.entity.paper.ExamPaperAllocation;
import com.smileshark.entity.paper.ExamPaperRelease;
import com.smileshark.entity.user.Student;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Score {
    private String studentId;
    private Student student;
    private String examPaperReleaseId;
    private ExamPaperRelease examPaperRelease;
    private String examPaperAllocationId;
    private ExamPaperAllocation examPaperAllocation;
    private Double score;
    private Integer finishTime;
}
