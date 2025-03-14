package com.smileshark.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ScoreMapper {
    int lastScoreByExamPaperAllocationId(@Param("examPaperAllocationId")String examPaperAllocationId);
    Double avgScoreByStudentId(@Param("studentId")String studentId);
}
