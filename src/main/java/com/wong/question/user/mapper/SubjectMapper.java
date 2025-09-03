package com.wong.question.user.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wong.question.user.entity.SubjectEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author wong
* @description 针对表【subject(科普题目)】的数据库操作Mapper
* @createDate 2025-08-09 15:54:38
* @Entity com.wong.question.user.entity.SubjectEntity
*/
@Mapper
public interface SubjectMapper extends BaseMapper<SubjectEntity> {
    Page<SubjectEntity> getSubjectList(IPage<SubjectEntity> page);
}




