package com.wong.question.user.mapper;

import com.wong.question.user.entity.SubjectAnswerEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author wong
* @description 针对表【subject_answer(科普题目答案)】的数据库操作Mapper
* @createDate 2025-08-09 15:54:38
* @Entity com.wong.question.user.entity.SubjectAnswerEntity
*/
@Mapper
public interface SubjectAnswerMapper extends BaseMapper<SubjectAnswerEntity> {
    List<SubjectAnswerEntity> getListById(@Param("ids") List<Integer> ids);
}




