package com.smileshark.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.smileshark.common.RequestParams;
import com.smileshark.common.Result;
import com.smileshark.entity.paper.ExamPaperAllocation;
import com.smileshark.mapper.ExamPaperAllocationMapper;
import com.smileshark.mapper.ScoreMapper;
import com.smileshark.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ScoreServiceImp implements ScoreService {
    private final ScoreMapper scoreMapper;
    private final ExamPaperAllocationMapper examPaperAllocationMapper;
    private final Result result;

    @Override
    public String lastScoreByExamPaperAllocationId(RequestParams requestParams) {
        Result result = Result.success();
        ExamPaperAllocation examPaperAllocation = examPaperAllocationMapper.selectLastExamPaperAllocationByStudentId(
                requestParams.getStudentId()
        );
        if(examPaperAllocation !=null){
            int i = scoreMapper.lastScoreByExamPaperAllocationId(examPaperAllocation.getExamPaperAllocationId());
            result.setData(i);
        }else{
            result.setData(0);
        }
        return JSONObject.toJSONString(result.setMsg(null), SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String avgScoreByStudentId(RequestParams requestParams) {
        Result result = Result.success(null);
        Double v = scoreMapper.avgScoreByStudentId(requestParams.getStudentId());
        result.setData(Objects.requireNonNullElse(v, 0));
        return JSONObject.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
    }
}
