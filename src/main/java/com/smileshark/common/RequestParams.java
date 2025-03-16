package com.smileshark.common;

import com.smileshark.entity.paper.ExamPaperAllocation;
import com.smileshark.entity.question.Question;
import com.smileshark.entity.user.Student;
import lombok.Data;
import org.apache.ibatis.javassist.runtime.Inner;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class RequestParams {
    private String studentName;
    private String studentAccount;
    private String studentPassword;
    private String administratorId;
    private String administratorAccount;
    private String administratorPassword;
    private String courseId;
    private String chapterId;
    private String subsectionId;
    private String questionContent;
    private Boolean vague;
    private List<Integer> questionTypes;
    private Integer page;
    private Integer size;
    private String questionId;
    private List<String> courseIds;
    private List<String> chapterIds;
    private List<String> subsectionIds;
    private Integer questionType0Count;
    private Integer questionType1Count;
    private Integer questionType2Count;
    private Integer questionType;
    private List<Question> questions;
    private String examPaperTitle;
    private Double totalScore;
    private String examPaperId;
    private String examStartTime;
    private String examEndTime;
    private String notes;
    private List<Student> students;
    private List<String> administratorIds;
    private List<Integer> examPaperStates;
    private String studentId;
    private Integer examPaperAllocationState;
    private String examPaperAllocationId;
    private ExamPaperAllocation examPaperAllocation;
}
