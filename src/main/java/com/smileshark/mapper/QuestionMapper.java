package com.smileshark.mapper;

import com.smileshark.entity.question.Question;
import com.smileshark.entity.question.Subsection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionMapper {
    int questionCount();
    List<Question> selectQuestionByParamsAndSubsectionId(
            @Param("subsectionId") String subsectionId,
            @Param("questionContent") String questionContent,
            @Param("questionTypes")List<Integer> questionTypes
    );
    List<Question> selectQuestionByParamsInSubsectionIds(
            @Param("subsections") List<Subsection> subsections,
            @Param("questionContent") String questionContent,
            @Param("questionTypes")List<Integer> questionTypes
    );
    List<Question> selectQuestionByParams(
            @Param("questionContent") String questionContent,
            @Param("questionTypes")List<Integer> questionTypes
    );
}
