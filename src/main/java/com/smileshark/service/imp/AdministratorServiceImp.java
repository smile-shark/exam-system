package com.smileshark.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.smileshark.common.RequestParams;
import com.smileshark.common.Result;
import com.smileshark.entity.user.Administrator;
import com.smileshark.mapper.AdministratorMapper;
import com.smileshark.service.AdministratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdministratorServiceImp implements AdministratorService {
    private final AdministratorMapper administratorMapper;
    @Override
    public String getAdministratorNameList() {
        return JSONObject.toJSONString(Result.success(administratorMapper.getAdministratorNameList()));
    }

    @Override
    public String adminLogin(RequestParams requestParams) {
        Result result = Result.error();
        Administrator administrator = administratorMapper.selectAdministratorByAccount(requestParams.getAdministratorAccount());
        if(administrator != null){
            if(administrator.getAdministratorPassword().equals(requestParams.getAdministratorPassword())){
                administrator.setAdministratorPassword(null);
                result=Result.success("登录成功").setData(administrator);
            }else {
                result.setMsg("密码错误");
            }
        }else {
            result.setMsg("没有该管理员账号");
        }
        return JSONObject.toJSONString(result);
    }
}
