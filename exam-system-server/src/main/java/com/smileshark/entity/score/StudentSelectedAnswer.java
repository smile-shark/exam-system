package com.smileshark.entity.score;

import com.smileshark.entity.question.Answer;
import lombok.Data;
import org.springframework.stereotype.Component;
@Data
@Component
public class StudentSelectedAnswer {
    private String answerId;
    private String answerRecordId;
    private Answer answer;
    private AnswerRecord answerRecord;
}
