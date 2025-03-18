package com.smileshark.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.smileshark.common.RequestParams;
import com.smileshark.common.Result;
import com.smileshark.entity.paper.ExamPaperAllocation;
import com.smileshark.entity.paper.ExamPaperRelease;
import com.smileshark.entity.user.Student;
import com.smileshark.mapper.ExamPaperAllocationMapper;
import com.smileshark.mapper.ExamPaperReleaseMapper;
import com.smileshark.service.ExamPaperReleaseService;
import com.smileshark.utils.CreateId;
import com.smileshark.utils.DateStrToLongUtil;
import com.smileshark.utils.TimesUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamPaperReleaseServiceImp implements ExamPaperReleaseService {
    private final ExamPaperReleaseMapper examPaperReleaseMapper;
    private final ExamPaperAllocationMapper examPaperAllocationMapper;
    @Override
    public String insertExamPaperRelease(RequestParams requestParams) {
        Result result = Result.error();
        // 先添加到发布表中
        String examPaperReleaseId = CreateId.createId();
        System.out.println(TimesUtils.getCurrentTime(requestParams.getDuration()));
        long examPaperReleaseTime = System.currentTimeMillis();
        int i = examPaperReleaseMapper.insertExamPaperRelease(new ExamPaperRelease() {{
            setExamPaperReleaseId(examPaperReleaseId);
            setExamPaperId(requestParams.getExamPaperId());
            setAdministratorId(requestParams.getAdministratorId());
            setExamPaperReleaseTime(examPaperReleaseTime);
            setExamStartTime(DateStrToLongUtil.dateStrToLong(requestParams.getExamStartTime()));
            setExamEndTime(DateStrToLongUtil.dateStrToLong(requestParams.getExamEndTime()));
            setDuration(TimesUtils.getCurrentTime(requestParams.getDuration()));
            setNotes(requestParams.getNotes());
        }});
        if(i > 0){
            // 然后再根据学生进行分配
            for (Student student : requestParams.getStudents()) {
                examPaperAllocationMapper.insertExamPaperAllocation(new ExamPaperAllocation() {{
                    setExamPaperAllocationId(CreateId.createId());
                    setStudentId(student.getStudentId());
                    setExamPaperReleaseId(examPaperReleaseId);
                }});
            }
            result = Result.success("发布成功");
        }
        return JSONObject.toJSONString(result);
    }
}
