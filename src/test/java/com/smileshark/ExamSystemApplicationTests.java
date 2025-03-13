package com.smileshark;

import com.smileshark.common.RequestParams;
import com.smileshark.service.QuestionService;
import com.smileshark.utils.CreateId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class ExamSystemApplicationTests {
    @Autowired
    private QuestionService questionService;

    @Test
    void contextLoads() {
    }
    @Test
    void createIdTest(){
        System.out.println(CreateId.createId());
    }
    @Test
    void questionListTest(){
        String s = questionService.questionListByCourseChapterSubsectionContentType(new RequestParams() {{
            setQuestionTypes(Arrays.asList(1, 2, 0));
            setPage(1);
            setSize(10);
            setQuestionContent("");
            setVague(true);
        }});
        System.out.println(s);
    }

}
