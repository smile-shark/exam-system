package com.smileshark.mapper;

import com.smileshark.entity.paper.QuestionOnTestPaper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionOnTestPaperMapper {
    int insertItem(@Param("questionOnTestPaper")QuestionOnTestPaper questionOnTestPaper);
    List<QuestionOnTestPaper> selectByExamPaperId(@Param("examPaperId") String examPaperId);
    int deleteByExamPaperId(@Param("examPaperId") String examPaperId);
}
