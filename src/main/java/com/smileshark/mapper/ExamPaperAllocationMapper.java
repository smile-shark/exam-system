package com.smileshark.mapper;

import com.smileshark.entity.paper.ExamPaper;
import com.smileshark.entity.paper.ExamPaperAllocation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ExamPaperAllocationMapper {
    int insertExamPaperAllocation(@Param("examPaperAllocation")ExamPaperAllocation examPaperAllocation);
    int allocationExamPaperCountByStudentIdAndState(
            @Param("studentId") String studentId,
            @Param("examPaperAllocationState") Integer examPaperAllocationState
    );
    ExamPaperAllocation selectLastExamPaperAllocationByStudentId(@Param("studentId") String studentId);
    int ExamCountByStudentId(@Param("studentId") String studentId);
    List<ExamPaperAllocation> selectExamPaperAllocationsByStudentIdParamState(
            @Param("studentId") String studentId,
            @Param("examPaperAllocationState") Integer examPaperAllocationState
    );
    int updateExamPaperAllocationStateTo1(
            @Param("nowTimes") long nowTimes
    );
    int updateExamPaperAllocationStateTo3(
            @Param("nowTimes") long nowTimes
    );
    ExamPaperAllocation selectExamPaperAllocationByExamPaperAllocationId(
            @Param("examPaperAllocationId") String examPaperAllocationId
    );
    int updateExamPaperAllocationStateToFinish(
            @Param("examPaperAllocationId")String examPaperAllocationId
    );
    List<ExamPaperAllocation> selectExamPaperAllocationsByStudentIdFinish(
            @Param("studentId") String studentId
    );
    ExamPaperAllocation selectExamPaperAllocationByExamPaperAllocationIdReview(
            @Param("examPaperAllocationId") String examPaperAllocationId
    );
}
