package com.smileshark.mapper;

import com.smileshark.entity.paper.ExamPaperAllocation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ExamPaperAllocationMapper {
    int insertExamPaperAllocation(@Param("examPaperAllocation")ExamPaperAllocation examPaperAllocation);
    int allocationExamPaperCountByStudentIdAndState(
            @Param("studentId") String studentId,
            @Param("examPaperAllocationState") Integer examPaperAllocationState
    );
    ExamPaperAllocation selectLastExamPaperAllocationByStudentId(@Param("studentId") String studentId);
    int ExamCountByStudentId(@Param("studentId") String studentId);
}
