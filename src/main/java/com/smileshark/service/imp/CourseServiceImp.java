package com.smileshark.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.smileshark.common.Result;
import com.smileshark.mapper.CourseMapper;
import com.smileshark.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseServiceImp implements CourseService {
    private final CourseMapper courseMapper;
    @Override
    public String courseCount() {
        return JSONObject.toJSONString(Result.success("获取成功",courseMapper.courseCount()));
    }

    @Override
    public String selectAllCoursesNameAndId() {
        return JSONObject.toJSONString(Result.success("获取成功",courseMapper.selectAllCoursesNameAndId()));
    }
}
