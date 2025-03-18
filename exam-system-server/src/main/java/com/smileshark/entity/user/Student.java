package com.smileshark.entity.user;

import com.smileshark.entity.paper.ExamPaperAllocation;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class Student {
    private String studentId;
    private String administratorId;
    private Administrator administrator;
    private String studentAccount;
    private String studentPassword;
    private String studentName;
    private List<ExamPaperAllocation> examPaperAllocations;
    private String token;
}
