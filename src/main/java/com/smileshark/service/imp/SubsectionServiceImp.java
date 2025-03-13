package com.smileshark.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.smileshark.common.RequestParams;
import com.smileshark.common.Result;
import com.smileshark.mapper.SubsectionMapper;
import com.smileshark.service.SubsectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
