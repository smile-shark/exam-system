package com.smileshark.controller;

import com.smileshark.common.RequestParams;
import com.smileshark.service.AdministratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdministratorController {
    private final AdministratorService administratorService;
    // 返回管理员姓名的列表
    @RequestMapping("/administrator/name/list")
    public String getAdministratorNameList() {
        return administratorService.getAdministratorNameList();
    }
    // 登录
    @RequestMapping("/administrator/login")
    public String login(@RequestBody RequestParams requestParams) {
        return administratorService.adminLogin(requestParams);
    }
}
