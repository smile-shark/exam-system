package com.smileshark.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.smileshark.common.RequestParams;
import com.smileshark.common.Result;
import com.smileshark.mapper.ExamPaperAllocationMapper;
import com.smileshark.service.ExamPaperAllocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamPaperAllocationServiceImp implements ExamPaperAllocationService {
    private final ExamPaperAllocationMapper examPaperAllocationMapper;
    @Override
    public String allocationExamPaperCountByStudentIdAndState(RequestParams requestParams) {
        return JSONObject.toJSONString(
                Result.success(null,examPaperAllocationMapper.allocationExamPaperCountByStudentIdAndState(
                        requestParams.getStudentId(),
                        requestParams.getExamPaperAllocationState()
                ))
        );
    }

    @Override
    public String ExamCountByStudentId(RequestParams requestParams) {
        return JSONObject.toJSONString(Result.success(null,examPaperAllocationMapper.ExamCountByStudentId(requestParams.getStudentId())));
    }
}
