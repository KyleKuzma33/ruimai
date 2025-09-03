package com.wong.question.user.mapper;

import com.wong.question.user.entity.UserAnswerLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author wong
* @description 针对表【user_answer_log】的数据库操作Mapper
* @createDate 2025-08-11 16:22:46
* @Entity com.wong.question.user.entity.UserAnswerLogEntity
*/
@Mapper
public interface UserAnswerLogMapper extends BaseMapper<UserAnswerLogEntity> {
    Integer getSumAllBySessionId(@Param("userId")Integer userId, @Param("sessionId")String sessionId);

    List<UserAnswerLogEntity> getAllBySubjectId(@Param("subjectId") Integer subjectId);

    List<UserAnswerLogEntity> getAllSumByUserId();

    List<UserAnswerLogEntity> getAll();
}




