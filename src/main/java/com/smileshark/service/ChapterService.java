package com.smileshark.service;

import com.smileshark.common.RequestParams;

public interface ChapterService {
    String selectAllChaptersNameTitleAndIdByCourseId(RequestParams requestParams);
}
