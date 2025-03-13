package com.smileshark.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.smileshark.common.Result;
import com.smileshark.mapper.ExamPaperMapper;
import com.smileshark.service.ExamPaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamPaperServiceImp implements ExamPaperService {
    public final ExamPaperMapper examPaperMapper;

    @Override
    public String examPaperCount() {
        return JSONObject.toJSONString(Result.success("获取成功",examPaperMapper.examPaperCount()));
    }
}
