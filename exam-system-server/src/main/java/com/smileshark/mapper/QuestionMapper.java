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
    List<Question> selectQuestionBySubsectionsAndTypeLimitNum(
            @Param("subsections") List<Subsection> subsections,
            @Param("questionType") Integer questionType,
            @Param("limitNum") Integer limitNum
    );
    List<Question> selectQuestionByTypeLimitNum(
            @Param("questionType") Integer questionType,
            @Param("limitNum") Integer limitNum
    );
    Question selectQuestionBySubsectionsAndTypeLimitNumNotQuestionId(
            @Param("subsections") List<Subsection> subsections,
            @Param("questionType") Integer questionType,
            @Param("questionId") String questionId
    );
    Question selectQuestionByTypeLimitNumNotQuestionId(
            @Param("questionType") Integer questionType,
            @Param("questionId") String questionId
    );
    Question selectQuestionByQuestionId(@Param("questionId") String questionId);
    Question selectQuestionByQuestionIdHaveReallyAnswer(@Param("questionId") String questionId);
    Question selectQuestionByQuestionIdAllAnswer(@Param("questionId") String questionId);
    int insertQuestion(@Param("questions")List<Question> questions);
    int deleteQuestionByQuestionId(@Param("questionId") String questionId);
    int updateQuestionByQuestionId(@Param("question") Question question);
}
