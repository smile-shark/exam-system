package com.smileshark.service;

import com.smileshark.common.RequestParams;

public interface AdministratorService {
    String getAdministratorNameList();
    String adminLogin(RequestParams requestParams);
}
