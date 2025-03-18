package com.smileshark.mapper;

import com.smileshark.entity.score.Score;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ScoreMapper {
    int lastScoreByExamPaperAllocationId(@Param("examPaperAllocationId")String examPaperAllocationId);
    Double avgScoreByStudentId(@Param("studentId")String studentId);
    int insertScore(@Param("score") Score score);
    Score selectScoreByExamPaperAllocationId(@Param("examPaperAllocationId")String examPaperAllocationId);
}
