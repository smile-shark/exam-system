package com.smileshark.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.smileshark.common.RequestParams;
import com.smileshark.common.Result;
import com.smileshark.mapper.ChapterMapper;
import com.smileshark.service.ChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChapterServiceImp implements ChapterService {
    private final ChapterMapper chapterMapper;
    @Override
    public String selectAllChaptersNameTitleAndIdByCourseId(RequestParams requestParams) {
        return JSONObject.toJSONString(
                Result.success("查询成功",
                        chapterMapper.selectAllChaptersNameTitleAndIdByCourseId(
                                requestParams.getCourseId()
                        )
                )
        );
    }
}
