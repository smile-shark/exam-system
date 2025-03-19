package com.smileshark.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.smileshark.common.RequestParams;
import com.smileshark.common.Result;
import com.smileshark.mapper.AnswerMapper;
import com.smileshark.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerServiceImp implements AnswerService {
    private final AnswerMapper answerMapper;
    @Override
    public String selectAnswerByQuestionId(RequestParams requestParams) {
        return JSONObject.toJSONString(Result.success("获取成功",answerMapper.selectAnswerByQuestionId(
                requestParams.getQuestionId()
        )), SerializerFeature.DisableCircularReferenceDetect);
    }
}
