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
            switch (chapter.getChapterTitle()){
                case "第一章":
                    chapter.setOrder(1);
                    break;
                case "第二章":
                    chapter.setOrder(2);
                    break;
                case "第三章":
                    chapter.setOrder(3);
                    break;
                case "第四章":
                    chapter.setOrder(4);
                    break;
                case "第五章":
                    chapter.setOrder(5);
                    break;
                case "第六章":
                    chapter.setOrder(6);
                    break;
                case "第七章":
                    chapter.setOrder(7);
                    break;
                case "第八章":
                    chapter.setOrder(8);
                    break;
                case "第九章":
                    chapter.setOrder(9);
                    break;
                case "第十章":
                    chapter.setOrder(10);
                    break;
                case "第十一章":
                    chapter.setOrder(11);
                    break;
                case "第十二章":
                    chapter.setOrder(12);
                    break;
                case "第十三章":
                    chapter.setOrder(13);
                    break;
                case "第十四章":
                    chapter.setOrder(14);
                    break;
                case "第十五章":
                    chapter.setOrder(15);
                    break;
                case "第十六章":
                    chapter.setOrder(16);
                    break;
                case "第十七章":
                    chapter.setOrder(17);
                    break;
                case "第十八章":
                    chapter.setOrder(18);
                    break;
                case "第十九章":
                    chapter.setOrder(19);
                    break;
                case "第二十章":
                    chapter.setOrder(20);
                    break;
                case "第二十一章":
                    chapter.setOrder(21);
                    break;
                case "第二十二章":
                    chapter.setOrder(22);
                    break;
                case "第二十三章":
                    chapter.setOrder(23);
                    break;
                case "第二十四章":
                    chapter.setOrder(24);
                    break;
                case "第二十五章":
                    chapter.setOrder(25);
                    break;
                case "第二十六章":
                    chapter.setOrder(26);
                    break;
                case "第二十七章":
                    chapter.setOrder(27);
                    break;
                case "第二十八章":
                    chapter.setOrder(28);
                    break;
                case "第二十九章":
                    chapter.setOrder(29);
                    break;
                case "第三十章":
                    chapter.setOrder(30);
                    break;
                case "第三十一章":
                    chapter.setOrder(31);
                    break;
                case "第三十二章":
                    chapter.setOrder(32);
                    break;
                case "第三十三章":
                    chapter.setOrder(33);
                    break;
                case "第三十四章":
                    chapter.setOrder(34);
                    break;
                default:
                    chapter.setOrder(0);
                    break;
            }
            for (Subsection subsection : chapter.getSubsections()) {
                String subsectionId = CreateId.createId();
                subsection.setChapterId(chapterId);
                subsection.setSubsectionId(subsectionId);
                subsections.add(subsection);
            }
        }
        if(courseMapper.insertCourse(course)>0){
            if(chapterMapper.insertChapter(chapters)>0){
                if(subsectionMapper.insertSubsection(subsections)>0){
                    result = Result.success("插入成功");
                }
            }
        }else{
            result.setMsg("插入失败");
        }
        return JSONObject.toJSONString(result);
    }
}
