package com.smileshark.mapper;

import com.smileshark.entity.score.AnswerRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AnswerRecordMapper {
    int answerRecordCountByStudentId(@Param("studentId")String studentId);
    int wrongQuestionRecordCountByStudentId(@Param("studentId")String studentId);
    int insertAnswerRecord(@Param("answerRecord")AnswerRecord answerRecord);
    List<AnswerRecord> selectAnswerRecordByExamPaperAllocationId(@Param("examPaperAllocationId")String examPaperAllocationId);
}
