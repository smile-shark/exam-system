package com.smileshark.mapper;

import com.smileshark.entity.paper.QuestionOnTestPaper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface QuestionOnTestPaperMapper {
    int insertItem(@Param("questionOnTestPaper")QuestionOnTestPaper questionOnTestPaper);
}
