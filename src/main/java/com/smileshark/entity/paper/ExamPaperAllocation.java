package com.smileshark.entity.paper;

import com.smileshark.entity.user.Student;
import lombok.Data;

@Data
public class ExamPaperAllocation {
    private String examPaperAllocationId;
    private String studentId;
    private Student student;
    private String examPaperReleaseId;
    private ExamPaperRelease examPaperRelease;
    private Integer examPaperAllocationState;
}

