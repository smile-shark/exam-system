package com.smileshark.entity.score;

import com.smileshark.entity.paper.ExamPaperAllocation;
import com.smileshark.entity.question.Answer;
import com.smileshark.entity.question.Question;
import com.smileshark.entity.user.Student;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class AnswerRecord {
    private String answerRecordId;
    private String examPaperAllocationId;
    private ExamPaperAllocation examPaperAllocation;
    private String questionId;
    private Question question;
    private String answerId;
    private Answer answer;
    private String studentId;
    private Student student;
    private Integer answerRecordTime;
    private Boolean isTrue;
}
