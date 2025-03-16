package com.smileshark.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smileshark.common.RequestParams;
import com.smileshark.common.Result;
import com.smileshark.entity.paper.ExamPaper;
import com.smileshark.entity.paper.ExamPaperAllocation;
import com.smileshark.mapper.ExamPaperAllocationMapper;
import com.smileshark.service.ExamPaperAllocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamPaperAllocationServiceImp implements ExamPaperAllocationService {
    private final ExamPaperAllocationMapper examPaperAllocationMapper;
    @Override
    public String allocationExamPaperCountByStudentIdAndState(RequestParams requestParams) {
        return JSONObject.toJSONString(
                Result.success(null,
                        examPaperAllocationMapper.allocationExamPaperCountByStudentIdAndState(
                        requestParams.getStudentId(),
                        requestParams.getExamPaperAllocationState()
                ))
        );
    }

    @Override
    public String ExamCountByStudentId(RequestParams requestParams) {
        return JSONObject.toJSONString(Result.success(null,examPaperAllocationMapper.ExamCountByStudentId(requestParams.getStudentId())));
    }

    @Override
    public String selectExamPaperAllocationsByStudentIdParamState(RequestParams requestParams) {
        Page<ExamPaperAllocation> page = PageHelper.startPage(requestParams.getPage(), requestParams.getSize());
        List<ExamPaperAllocation> examPaperAllocations = examPaperAllocationMapper.selectExamPaperAllocationsByStudentIdParamState(
                requestParams.getStudentId(),
                requestParams.getExamPaperAllocationState()
        );
        return JSONObject.toJSONString(Result.success(null,
                PageInfo.of(page)
        ));
    }

    @Override
    public String selectExamPaperAllocationByExamPaperAllocationId(RequestParams requestParams) {
        Result result = Result.error();
        ExamPaperAllocation examPaperAllocation = examPaperAllocationMapper.selectExamPaperAllocationByExamPaperAllocationId(requestParams.getExamPaperAllocationId());
        if(examPaperAllocation == null){
            result.setMsg("没有需要考试的试卷");
        }else{
            result=Result.success("获取成功",examPaperAllocation);
        }
        return JSONObject.toJSONString(result);
    }

    @Override
    public String selectExamPaperAllocationsByStudentIdFinish(RequestParams requestParams) {
        Page<ExamPaperAllocation> page = PageHelper.startPage(requestParams.getPage(), requestParams.getSize());
        List<ExamPaperAllocation> examPaperAllocations = examPaperAllocationMapper.selectExamPaperAllocationsByStudentIdFinish(
                requestParams.getStudentId()
        );
        return JSONObject.toJSONString(Result.success(null,
                PageInfo.of(page)
        ));
    }

    @Override
    public String selectExamPaperAllocationByExamPaperAllocationIdReview(RequestParams requestParams) {
        Result result = Result.error();
        ExamPaperAllocation examPaperAllocation = examPaperAllocationMapper.selectExamPaperAllocationByExamPaperAllocationIdReview(requestParams.getExamPaperAllocationId());
        if(examPaperAllocation == null){
            result.setMsg("没有该试卷的记录");
        }else{
            result=Result.success("获取成功",examPaperAllocation);
        }
        return JSONObject.toJSONString(result);
    }
}
