package com.smileshark;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.smileshark.mapper")
@EnableAspectJAutoProxy
@ServletComponentScan("com.smileshark.filter")
public class ExamSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExamSystemApplication.class, args);
    }

}
/*
create table student_selected_answer
        (
                answer_id        char(32) null comment '学生选择的对应的答案id',
answer_record_id char(32) null comment '对应学生的答题记录id',
constraint student_selected_answer_answer_answer_id_fk
foreign key (answer_id) references answer (answer_id),
constraint student_selected_answer_answer_record_answer_record_id_fk
foreign key (answer_record_id) references answer_record (answer_record_id)
        )
comment '学生选择的答案';
*/

