package com.smileshark.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.smileshark.common.RequestParams;
import com.smileshark.common.Result;
import com.smileshark.entity.question.Subsection;
import com.smileshark.mapper.SubsectionMapper;
import com.smileshark.service.SubsectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
public class SubsectionServiceImp implements SubsectionService {
    private final SubsectionMapper subsectionMapper;
    @Override
    public String selectAllSubsectionsNameAndIdByChapterId(RequestParams requestParams) {
        return JSONObject.toJSONString(Result.success("查询成功",
                subsectionMapper.selectAllSubsectionsNameAndIdByChapterId(
                        requestParams.getChapterId()
                )
            )
        );
    }

    @Override
    public String selectAllSubsectionInfoBySubsectionId(RequestParams requestParams) {
        return JSONObject.toJSONString(Result.success("查询成功",subsectionMapper.selectAllSubsectionInfoBySubsectionId(
                requestParams.getSubsectionId()
        )));
    }

    @Override
    public String selectSubsectionsInChapterIds(RequestParams requestParams) {
        return JSONObject.toJSONString(Result.success(null,subsectionMapper.selectSubsectionsInChapterIds(
                requestParams.getChapterIds()
        )));
    }

    @Override
    public String insertSubsection(RequestParams requestParams) {
        Subsection subsection = requestParams.getSubsection();
        subsection.setChapterId(requestParams.getChapterId());
        subsectionMapper.insertSubsections(
                new CopyOnWriteArrayList<>(){{
                    add(subsection);
                }}
        );
        return JSONObject.toJSONString(Result.success("插入成功"));
    }

    @Override
    public String updateSubsection(RequestParams requestParams) {
        int i = subsectionMapper.updateSubsection(requestParams.getSubsection());
        return JSONObject.toJSONString(Result.success("更新成功"));
    }

    @Override
    public String deleteSubsectionBySubsectionId(RequestParams requestParams) {
        int i = subsectionMapper.deleteSubsectionBySubsectionId(requestParams.getSubsectionId());
        return JSONObject.toJSONString(Result.success("删除成功"));
    }
}
