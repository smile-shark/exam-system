package com.smileshark.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.smileshark.common.Result;
import com.smileshark.mapper.QuestionMapper;
import com.smileshark.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionServiceImp implements QuestionService {
    private final QuestionMapper questionMapper;
    @Override
    public String questionCount() {
        return JSONObject.toJSONString(Result.success("获取成功",questionMapper.questionCount()));
    }
}
