package com.smileshark.mapper;

import com.smileshark.entity.paper.ExamPaper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ExamPaperMapper {
    int examPaperCount();
    int insertExamPaper(@Param("examPaper")ExamPaper examPaper);
    List<ExamPaper> selectExamPaperByTitle(@Param("examPaperTitle") String examPaperTitle);
    List<ExamPaper> selectAllExamPaperIdTitle();
    List<ExamPaper> selectExamPaperByParams(
            @Param("administratorIds")List<String> administratorIds,
            @Param("examPaperStates")List<Integer> examPaperStates,
            @Param("examPaperTitle")String examPaperTitle
    );
    ExamPaper selectExamPaperByExamPaperId(@Param("examPaperId")String examPaperId);
    ExamPaper selectExamPaperByExamPaperIdToExam(@Param("examPaperId")String examPaperId);
    int updateExamPaperToCancel(@Param("examPaperId")String examPaperId);
    int deleteExamPaperById(@Param("examPaperId")String examPaperId);
}
