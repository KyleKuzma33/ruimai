package com.wong.question.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wong.question.user.entity.SubjectEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wong.question.utils.R;

import java.util.List;

/**
* @author wong
* @description 针对表【subject(科普题目)】的数据库操作Service
* @createDate 2025-08-09 15:54:38
*/
public interface SubjectService extends IService<SubjectEntity> {

    IPage<SubjectEntity> getListPage(int current, int size);

    R addSubject(SubjectEntity subjectEntity);

    R delById(Integer id);

    R getSubNow();

    R setSubNow(Integer subjectId);

    Boolean verificationAnswer(Integer subjectId, String answer);

    R startSub(Integer subjectId);

    Boolean getCountdown(Integer subjectId);
}
