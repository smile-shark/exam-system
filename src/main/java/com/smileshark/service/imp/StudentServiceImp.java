package com.smileshark.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.smileshark.common.RequestParams;
import com.smileshark.common.Result;
import com.smileshark.entity.user.Student;
import com.smileshark.mapper.ExamPaperAllocationMapper;
import com.smileshark.mapper.StudentMapper;
import com.smileshark.service.StudentService;
import com.smileshark.utils.CreateId;
import com.smileshark.utils.DateStrToLongUtil;
import com.smileshark.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImp implements StudentService {
    private final StudentMapper studentMapper;
    private final ExamPaperAllocationMapper examPaperAllocationMapper;
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
                student.setToken(JwtUtils.createJwt(student));
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

    @Override
    public String selectStudentInStudentIds(RequestParams requestParams) {
        List<Student> students = studentMapper.selectStudentInStudentIds(
                requestParams.getStudents().stream()
                        .map(Student::getStudentId)
                        .toList()
        );
        for (Student student : students) {
            student.setExamPaperAllocations(
                    examPaperAllocationMapper.selectExamPaperAllocationsInStudentIdAndBetweenTime(
                            List.of(student.getStudentId()),
                            DateStrToLongUtil.dateStrToLong(requestParams.getExamStartTime()),
                            DateStrToLongUtil.dateStrToLong(requestParams.getExamEndTime())
                    )
            );
        }
        return JSONObject.toJSONString(
                Result.success("查询成功",
                        students)
        );
    }
}
