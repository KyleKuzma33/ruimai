package com.wong.question.user.service;

import com.wong.question.user.entity.SubjectAnswerEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wong.question.utils.R;

import java.util.List;

/**
* @author wong
* @description 针对表【subject_answer(科普题目答案)】的数据库操作Service
* @createDate 2025-08-09 15:54:38
*/
public interface SubjectAnswerService extends IService<SubjectAnswerEntity> {
    Boolean delBySubId(Integer subjectId);

    List<SubjectAnswerEntity> getListById(List<Integer> ids);

    List<SubjectAnswerEntity> getListBySubjectId(Integer subjectId);
}
