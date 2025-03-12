package com.smileshark.entity.paper;

import com.smileshark.entity.question.Question;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class QuestionOnTestPaper {
    private String questionId;
    private Question question;
    private String examPaperId;
    private ExamPaper examPaper;
    private Integer questionOrderNumber;
}
