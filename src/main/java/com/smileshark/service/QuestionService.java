package com.smileshark.service;

import com.smileshark.common.RequestParams;
import com.smileshark.entity.question.Question;

import java.util.List;

public interface QuestionService {
    String questionCount();
    String questionListByCourseChapterSubsectionContentType(RequestParams requestParams);
}
