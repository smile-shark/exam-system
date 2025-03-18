package com.smileshark.service;

import com.smileshark.common.RequestParams;

public interface SubsectionService {
    String selectAllSubsectionsNameAndIdByChapterId(RequestParams requestParams);
    String selectAllSubsectionInfoBySubsectionId(RequestParams requestParams);
    String selectSubsectionsInChapterIds(RequestParams requestParams);
    String insertSubsection(RequestParams requestParams);
    String updateSubsection(RequestParams requestParams);
    String deleteSubsectionBySubsectionId(RequestParams requestParams);
}
