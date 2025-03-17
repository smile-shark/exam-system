package com.smileshark.mapper;

import com.smileshark.entity.user.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.aspectj.lang.annotation.Pointcut;

import java.util.List;

@Mapper
public interface StudentMapper {
    int insertStudent(@Param("student")Student student);
    Student selectStudentByAccount(@Param("account")String account);
    int selectStudentCount();
    List<Student> selectAllStudent();
    Student selectStudentByStudentId(@Param("studentId")String studentId);
    List<Student> selectStudentInStudentIds(@Param("studentIds")List<String> studentIds);
}
