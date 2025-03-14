package com.smileshark.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.smileshark.common.RequestParams;
import com.smileshark.common.Result;
import com.smileshark.entity.user.Student;
import com.smileshark.mapper.StudentMapper;
import com.smileshark.service.StudentService;
import com.smileshark.utils.CreateId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImp implements StudentService {
    private final StudentMapper studentMapper;
    @Override
    public String studentSignup(RequestParams requestParams) {
        Result result = Result.error();
        // 先确认数据库中是否已经有了这个账号
        Student student = studentMapper.selectStudentByAccount(requestParams.getStudentAccount());
        if (student == null) {
            if(studentMapper.insertStudent(new Student(){{
                setStudentId(CreateId.createId());
                setAdministratorId(requestParams.getAdministratorId());
                setStudentName(requestParams.getStudentName());
                setStudentPassword(requestParams.getStudentPassword());
                setStudentAccount(requestParams.getStudentAccount());
            }})>0){
                result = Result.success("注册成功");
            }else{
                result.setMsg("注册失败");
            }
        }else{
            result.setMsg("该账号已被注册");
        }
        return JSONObject.toJSONString(result);
    }

    @Override
    public String studentLogin(RequestParams requestParams) {
        Result result = Result.error();
        Student student = studentMapper.selectStudentByAccount(requestParams.getStudentAccount());
        if(student!=null){
            if(student.getStudentPassword().equals(requestParams.getStudentPassword())){
                student.setStudentPassword(null);
                result=Result.success("登录成功",student);
            }else {
                result.setMsg("密码错误");
            }
        }else {
            result.setMsg("没有该账号");
        }
        return JSONObject.toJSONString(result);
    }

    @Override
    public String studentCount() {
        return JSONObject.toJSONString(Result.success("获取成功",studentMapper.selectStudentCount()));
    }

    @Override
    public String selectAllStudent() {
        return JSONObject.toJSONString(Result.success("获取成功",studentMapper.selectAllStudent()));
    }
}
