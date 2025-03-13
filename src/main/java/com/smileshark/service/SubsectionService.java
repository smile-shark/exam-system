package com.smileshark.service;

import com.smileshark.common.RequestParams;

public interface SubsectionService {
    String selectAllSubsectionsNameAndIdByChapterId(RequestParams requestParams);
    String selectAllSubsectionInfoBySubsectionId(RequestParams requestParams);
}
