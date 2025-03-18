package com.smileshark.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smileshark.common.RequestParams;
import com.smileshark.common.Result;
import com.smileshark.entity.question.Chapter;
import com.smileshark.entity.question.Course;
import com.smileshark.entity.question.Subsection;
import com.smileshark.mapper.ChapterMapper;
import com.smileshark.mapper.CourseMapper;
import com.smileshark.mapper.SubsectionMapper;
import com.smileshark.service.CourseService;
import com.smileshark.utils.ChapterTitleUtil;
import com.smileshark.utils.CreateId;
import com.smileshark.utils.VagueUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImp implements CourseService {
    private final CourseMapper courseMapper;
    private final ChapterMapper chapterMapper;
    private final SubsectionMapper subsectionMapper;
    @Override
    public String courseCount() {
        return JSONObject.toJSONString(Result.success("获取成功",courseMapper.courseCount()));
    }

    @Override
    public String selectAllCoursesNameAndId() {
        return JSONObject.toJSONString(Result.success("获取成功",courseMapper.selectAllCoursesNameAndId()));
    }

    @Override
    public String selectAllCoursesAndChapters(RequestParams requestParams) {
        if(requestParams.getVague()){
            requestParams.setCourseName(
                    VagueUtil.createVagueString(
                            requestParams.getCourseName()
                    )
            );
        }

        Page<Course> page = PageHelper.startPage(requestParams.getPage(), requestParams.getSize());
        List<Course> courses = courseMapper.selectAllCoursesAndChapters(requestParams.getCourseName());
        return JSONObject
                .toJSONString(
                        Result.success("获取成功",
                                PageInfo.of(page))
                );
    }

    @Override
    public String insertCourse(RequestParams requestParams) {
        Result result = Result.error();
        String courseId = CreateId.createId();
        Course course = requestParams.getCourse();
        course.setCourseId(courseId);

        List<Chapter> chapters=course.getChapters();
        List<Subsection> subsections=new ArrayList<>();
        for(Chapter chapter:chapters){
            String chapterId = CreateId.createId();
            chapter.setCourseId(courseId);
            chapter.setChapterId(chapterId);
            chapter.setOrder(ChapterTitleUtil.getChapterTitle(chapter.getChapterTitle()));
            for (Subsection subsection : chapter.getSubsections()) {
                String subsectionId = CreateId.createId();
                subsection.setChapterId(chapterId);
                subsection.setSubsectionId(subsectionId);
                subsections.add(subsection);
            }
        }
        if(courseMapper.insertCourse(course)>0){
            if(chapterMapper.insertChapters(chapters)>0){
                if(subsectionMapper.insertSubsections(subsections)>0){
                    result = Result.success("插入成功");
                }
            }
        }else{
            result.setMsg("插入失败");
        }
        return JSONObject.toJSONString(result);
    }

    @Override
    public String updateCourse(RequestParams requestParams) {
        int i = courseMapper.updateCourse(requestParams.getCourse());
        return JSONObject.toJSONString(Result.success("更新成功"));
    }

    @Override
    public String deleteCourseByCourseId(RequestParams requestParams) {
        int i = courseMapper.deleteCourseByCourseId(requestParams.getCourseId());
        return JSONObject.toJSONString(Result.success("删除成功"));
    }
}
