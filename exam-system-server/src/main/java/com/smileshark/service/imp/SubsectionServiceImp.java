package com.smileshark.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.smileshark.common.RequestParams;
import com.smileshark.common.Result;
import com.smileshark.entity.question.Question;
import com.smileshark.entity.question.Subsection;
import com.smileshark.mapper.QuestionMapper;
import com.smileshark.mapper.SubsectionMapper;
import com.smileshark.service.QuestionService;
import com.smileshark.service.SubsectionService;
import com.smileshark.utils.CreateId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
public class SubsectionServiceImp implements SubsectionService {
    private final SubsectionMapper subsectionMapper;
    private final QuestionService questionService;
    private final QuestionMapper questionMapper;
    @Override
    public String selectAllSubsectionsNameAndIdByChapterId(RequestParams requestParams) {
        return JSONObject.toJSONString(Result.success("查询成功",
                subsectionMapper.selectAllSubsectionsNameAndIdByChapterId(
                        requestParams.getChapterId()
                )
            )
                , SerializerFeature.DisableCircularReferenceDetect );
    }

    @Override
    public String selectAllSubsectionInfoBySubsectionId(RequestParams requestParams) {
        return JSONObject.toJSONString(Result.success("查询成功",subsectionMapper.selectAllSubsectionInfoBySubsectionId(
                requestParams.getSubsectionId()
        )), SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String selectSubsectionsInChapterIds(RequestParams requestParams) {
        return JSONObject.toJSONString(Result.success(null,subsectionMapper.selectSubsectionsInChapterIds(
                requestParams.getChapterIds()
        )), SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String insertSubsection(RequestParams requestParams) {
        Subsection subsection = requestParams.getSubsection();
        subsection.setSubsectionId(CreateId.createId());
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
        List<Question> questions = questionMapper.selectQuestionBySubsectionId(requestParams.getSubsectionId());
        System.out.println(questions);
        for (Question question : questions) {
            questionService.deleteQuestionByQuestionId(new RequestParams(){{
                setQuestionId(question.getQuestionId());
            }});
        }
        int i = subsectionMapper.deleteSubsectionBySubsectionId(requestParams.getSubsectionId());
        return JSONObject.toJSONString(Result.success("删除成功"));
    }
}
