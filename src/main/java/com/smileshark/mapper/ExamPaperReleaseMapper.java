package com.smileshark.mapper;

import com.smileshark.entity.paper.ExamPaperRelease;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ExamPaperReleaseMapper {
    int insertExamPaperRelease(@Param("examPaperRelease") ExamPaperRelease examPaperRelease);
}
