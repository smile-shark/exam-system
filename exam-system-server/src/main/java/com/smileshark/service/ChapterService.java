package com.smileshark.service;

import com.smileshark.common.RequestParams;

public interface ChapterService {
    String selectAllChaptersNameTitleAndIdByCourseId(RequestParams requestParams);
    String selectAllChaptersNameTitleAndIdInCourseId(RequestParams requestParams);
    String insertChapters(RequestParams requestParams);
    String updateChapter(RequestParams requestParams);
    String deleteChapter(RequestParams requestParams);
}
