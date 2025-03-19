package com.smileshark.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.smileshark.common.RequestParams;
import com.smileshark.common.Result;
import com.smileshark.entity.user.Administrator;
import com.smileshark.mapper.AdministratorMapper;
import com.smileshark.service.AdministratorService;
import com.smileshark.utils.AESUtils;
import com.smileshark.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdministratorServiceImp implements AdministratorService {
    private final AdministratorMapper administratorMapper;
    @Override
    public String getAdministratorNameList() {
        return JSONObject.toJSONString(Result.success(null,administratorMapper.getAdministratorNameList()), SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String adminLogin(RequestParams requestParams) {
        Result result = Result.error();
        Administrator administrator = administratorMapper.selectAdministratorByAccount(requestParams.getAdministratorAccount());
        if(administrator != null){
            if(administrator.getAdministratorPassword().equals(AESUtils.decrypt(requestParams.getAdministratorPassword()))){
                administrator.setAdministratorPassword(null);
                administrator.setToken(JwtUtils.createJwt(administrator));
                result=Result.success("登录成功").setData(administrator);
            }else {
                result.setMsg("密码错误");
            }
        }else {
            result.setMsg("没有该管理员账号");
        }
        return JSONObject.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
    }
}
