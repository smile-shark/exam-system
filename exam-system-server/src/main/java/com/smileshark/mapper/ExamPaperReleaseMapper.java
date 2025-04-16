package com.smileshark.mapper;

import com.smileshark.entity.paper.ExamPaperRelease;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ExamPaperReleaseMapper {
    int insertExamPaperRelease(@Param("examPaperRelease") ExamPaperRelease examPaperRelease);
    ExamPaperRelease selectExamPaperReleaseByExamPaperReleaseId(@Param("examPaperReleaseId")String examPaperReleaseId);
    ExamPaperRelease selectExamPaperReleaseByExamPaperReleaseIdToExam(@Param("examPaperReleaseId")String examPaperReleaseId);

    List<ExamPaperRelease> selectByExamPaperId(String examPaperId);
}
