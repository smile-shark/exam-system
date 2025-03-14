package com.smileshark.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AnswerRecordMapper {
    int answerRecordCountByStudentId(@Param("studentId")String studentId);
    int wrongQuestionRecordCountByStudentId(@Param("studentId")String studentId);

}
