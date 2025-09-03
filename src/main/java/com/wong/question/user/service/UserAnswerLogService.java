package com.wong.question.user.service;

import com.wong.question.user.entity.UserAnswerLogEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wong.question.utils.R;

import java.util.List;

/**
* @author wong
* @description 针对表【user_answer_log】的数据库操作Service
* @createDate 2025-08-11 16:22:46
*/
public interface UserAnswerLogService extends IService<UserAnswerLogEntity> {
    R addAnswer(UserAnswerLogEntity userAnswerLogEntity);

    Integer getSumAllBySessionId(Integer userId, String sessionId);

    List<UserAnswerLogEntity> getAllBySubjectId(Integer subjectId);

    List<UserAnswerLogEntity> getAll();
}
