package com.smileshark.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.smileshark.common.RequestParams;
import com.smileshark.common.Result;
import com.smileshark.entity.question.Chapter;
import com.smileshark.entity.question.Subsection;
import com.smileshark.mapper.ChapterMapper;
import com.smileshark.mapper.SubsectionMapper;
import com.smileshark.service.ChapterService;
import com.smileshark.service.SubsectionService;
import com.smileshark.utils.ChapterTitleUtil;
import com.smileshark.utils.CreateId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
public class ChapterServiceImp implements ChapterService {
    private final ChapterMapper chapterMapper;
    private final SubsectionMapper subsectionMapper;
    private final SubsectionService subsectionService;
    @Override
    public String selectAllChaptersNameTitleAndIdByCourseId(RequestParams requestParams) {
        return JSONObject.toJSONString(
                Result.success("查询成功",
                        chapterMapper.selectAllChaptersNameTitleAndIdByCourseId(
                                requestParams.getCourseId()
                        )
                )
                , SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String selectAllChaptersNameTitleAndIdInCourseId(RequestParams requestParams) {
        return JSONObject.toJSONString(
                Result.success("查询成功",
                        chapterMapper.selectChaptersInCourseIds(
                                requestParams.getCourseIds()
                        )
                )
                , SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String insertChapters(RequestParams requestParams) {
        Chapter chapter = requestParams.getChapter();
        Result result = Result.error();
        String chapterId = CreateId.createId();
        chapter.setChapterId(chapterId);
        chapter.setOrder(ChapterTitleUtil.getChapterTitle(chapter.getChapterTitle()));
        if(chapterMapper.insertChapters(new CopyOnWriteArrayList<>(){{
            add(chapter);
        }})>0){
            for (Subsection subsection : chapter.getSubsections()) {
                subsection.setChapterId(chapterId);
                subsection.setSubsectionId(CreateId.createId());
            }
            if(subsectionMapper.insertSubsections(chapter.getSubsections())>0){
                result = Result.success("插入成功");
            }
        }
        return JSONObject.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String updateChapter(RequestParams requestParams) {
        int i = chapterMapper.updateChapter(requestParams.getChapter());
        return JSONObject.toJSONString(Result.success("更新成功"));
    }

    @Override
    public String deleteChapter(RequestParams requestParams) {
        List<Subsection> subsections = subsectionMapper.selectAllSubsectionsIdByChapterId(requestParams.getChapterId());
        for (Subsection subsection : subsections) {
            subsectionService.deleteSubsectionBySubsectionId(new RequestParams(){{
                setSubsectionId(subsection.getSubsectionId());
            }});
        }
        int i = chapterMapper.deleteChapter(requestParams.getChapterId());
        return JSONObject.toJSONString(Result.success("删除成功"));
    }
}
