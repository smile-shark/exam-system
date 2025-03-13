package com.smileshark.mapper;

import com.smileshark.entity.user.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.aspectj.lang.annotation.Pointcut;

@Mapper
public interface StudentMapper {
    int insertStudent(@Param("student")Student student);
    Student selectStudentByAccount(@Param("account")String account);
    int selectStudentCount();
}
