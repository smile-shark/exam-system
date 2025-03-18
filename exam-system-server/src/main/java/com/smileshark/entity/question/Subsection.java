package com.smileshark.entity.question;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Subsection {
    private String subsectionId;
    private String chapterId;
    private Chapter chapter;
    private String subsectionName;
}
