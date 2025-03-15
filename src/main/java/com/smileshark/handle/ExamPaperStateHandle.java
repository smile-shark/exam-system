package com.smileshark.handle;

import com.smileshark.mapper.ExamPaperAllocationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
// 调用接口处理试卷的状态
@RequiredArgsConstructor
public class ExamPaperStateHandle {
    private final ExamPaperAllocationMapper examPaperAllocationMapper;
    public void handle(){
        long nowTimes = System.currentTimeMillis();
        examPaperAllocationMapper.updateExamPaperAllocationStateTo1(nowTimes);
        examPaperAllocationMapper.updateExamPaperAllocationStateTo3(nowTimes);
    }
}
