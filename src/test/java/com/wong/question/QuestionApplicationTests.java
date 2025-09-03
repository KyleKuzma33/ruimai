package com.wong.question;

import com.wong.question.user.service.SubjectService;
import com.wong.question.utils.R;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class QuestionApplicationTests {

    @Resource
    private SubjectService subjectService;

    @Test
    void setSubNow() {
        subjectService.setSubNow(218);
    }

    @Test
    void getSubNow() {
        R subNow = subjectService.getSubNow();
        System.out.println(subNow.getData());
    }

}
