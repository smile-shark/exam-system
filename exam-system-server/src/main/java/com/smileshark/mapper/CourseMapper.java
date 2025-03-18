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
    List<Course> selectAllCoursesAndChapters(@Param("courseName")String courseName);
    int insertCourse(@Param("course") Course course);
    int updateCourse(@Param("course") Course course);
    int deleteCourseByCourseId(@Param("courseId") String courseId);
}
