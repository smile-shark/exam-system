package com.smileshark;

import com.smileshark.utils.CreateId;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExamSystemApplicationTests {

    @Test
    void contextLoads() {
    }
    @Test
    void createIdTest(){
        System.out.println(CreateId.createId());
    }

}
