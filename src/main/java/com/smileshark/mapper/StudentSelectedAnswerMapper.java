package com.smileshark.mapper;

import com.smileshark.entity.score.StudentSelectedAnswer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentSelectedAnswerMapper {
    int insertStudentSelectedAnswer(@Param("studentSelectedAnswer")StudentSelectedAnswer selectedAnswer);
    List<StudentSelectedAnswer> selectStudentSelectedAnswerByAnswerRecordId(@Param("answerRecordId") String answerRecordId);
}
