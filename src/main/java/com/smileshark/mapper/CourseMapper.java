package com.smileshark.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseMapper {
    int courseCount();
}
