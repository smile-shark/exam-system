package com.smileshark.entity.question;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Answer {
    private String answerId;
    private String questionId;
    private Question question;
    private String answer;
    private Boolean isTrue;
}
