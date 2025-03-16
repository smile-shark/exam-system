package com.smileshark.mapper;

import com.smileshark.entity.question.Answer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AnswerMapper {
    List<Answer> selectAnswerByQuestionId(@Param("questionId") String questionId);
    List<Answer> selectAnswerByQuestionIdNotHaveIsTrue(@Param("questionId") String questionId);
    List<Answer> selectAnswerByQuestionIdOnlyHaveTrue(@Param("questionId") String questionId);

}
