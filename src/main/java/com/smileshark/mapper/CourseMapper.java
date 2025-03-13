package com.smileshark.mapper;

import com.smileshark.entity.question.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseMapper {
    int courseCount();
    List<Course> selectAllCoursesNameAndId();
    Course selectCourseByCourseId(@Param("courseId") String courseId);
}
