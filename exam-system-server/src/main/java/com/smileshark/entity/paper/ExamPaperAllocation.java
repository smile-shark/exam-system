package com.smileshark.entity.paper;

import com.smileshark.entity.score.AnswerRecord;
import com.smileshark.entity.score.Score;
import com.smileshark.entity.user.Student;
import lombok.Data;

import java.util.List;

@Data
public class ExamPaperAllocation {
    private String examPaperAllocationId;
    private String studentId;
    private Student student;
    private String examPaperReleaseId;
    private ExamPaperRelease examPaperRelease;
    private Integer examPaperAllocationState;
    private Score score;
    private List<AnswerRecord> answerRecords;
}

