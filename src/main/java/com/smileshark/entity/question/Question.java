package com.smileshark.entity.question;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class Question {
    private String questionId;
    private String subsectionId;
    private Subsection subsection;
    private String question;
    private Integer questionType;
    private List<Answer> answers;
    private String selectedId;
    private List<String> selectedIds;
}
