package com.smileshark.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smileshark.common.RequestParams;
import com.smileshark.common.Result;
import com.smileshark.entity.paper.ExamPaperAllocation;
import com.smileshark.entity.user.Student;
import com.smileshark.mapper.ExamPaperAllocationMapper;
import com.smileshark.service.ExamPaperAllocationService;
import com.smileshark.utils.DateStrToLongUtil;
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
                , SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String ExamCountByStudentId(RequestParams requestParams) {
        return JSONObject.toJSONString(Result.success(null,
                examPaperAllocationMapper.ExamCountByStudentId(requestParams.getStudentId()))
                , SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String selectExamPaperAllocationsByStudentIdParamState(RequestParams requestParams) {
        Page<ExamPaperAllocation> page = PageHelper.startPage(requestParams.getPage(), requestParams.getSize());
        List<ExamPaperAllocation> examPaperAllocations = examPaperAllocationMapper.selectExamPaperAllocationsByStudentIdParamState(
                requestParams.getStudentId(),
                requestParams.getExamPaperAllocationState()
        );
        PageInfo<ExamPaperAllocation> examPaperAllocationPageInfo = PageInfo.of(page);
        return JSONObject.toJSONString(Result.success(null,
                examPaperAllocationPageInfo
        ), SerializerFeature.DisableCircularReferenceDetect);
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
        return JSONObject.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String selectExamPaperAllocationsByStudentIdFinish(RequestParams requestParams) {
        Page<ExamPaperAllocation> page = PageHelper.startPage(requestParams.getPage(), requestParams.getSize());
        List<ExamPaperAllocation> examPaperAllocations = examPaperAllocationMapper.selectExamPaperAllocationsByStudentIdFinish(
                requestParams.getStudentId()
        );
        return JSONObject.toJSONString(Result.success(null,
                PageInfo.of(page)
        ), SerializerFeature.DisableCircularReferenceDetect);
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
        return JSONObject.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String selectExamPaperAllocationsInStudentIdAndBetweenTime(RequestParams requestParams) {
        return JSONObject.toJSONString(Result.success("查询成功",
                examPaperAllocationMapper.selectExamPaperAllocationsInStudentIdAndBetweenTime(
                        requestParams.getStudents().stream()
                                .map(Student::getStudentId)
                                .toList(),
                        DateStrToLongUtil.dateStrToLong(requestParams.getExamStartTime()),
                        DateStrToLongUtil.dateStrToLong(requestParams.getExamEndTime())
                )
        ), SerializerFeature.DisableCircularReferenceDetect);
    }
}
