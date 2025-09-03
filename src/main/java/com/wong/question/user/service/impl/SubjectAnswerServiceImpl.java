package com.wong.question.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wong.question.user.entity.SubjectAnswerEntity;
import com.wong.question.user.service.SubjectAnswerService;
import com.wong.question.user.mapper.SubjectAnswerMapper;
import com.wong.question.utils.R;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author wong
* @description 针对表【subject_answer(科普题目答案)】的数据库操作Service实现
* @createDate 2025-08-09 15:54:38
*/
@Service
public class SubjectAnswerServiceImpl extends ServiceImpl<SubjectAnswerMapper, SubjectAnswerEntity>
    implements SubjectAnswerService{

    @Override
    public Boolean delBySubId(Integer subjectId) {
        LambdaQueryWrapper<SubjectAnswerEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SubjectAnswerEntity::getSubjectId, subjectId);
        return baseMapper.delete(queryWrapper) > 0;
    }

    @Override
    public List<SubjectAnswerEntity> getListById(List<Integer> ids) {
        return baseMapper.getListById(ids);
    }

    @Override
    public List<SubjectAnswerEntity> getListBySubjectId(Integer subjectId) {
        LambdaQueryWrapper<SubjectAnswerEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SubjectAnswerEntity::getSubjectId, subjectId);
        return baseMapper.selectList(queryWrapper);
    }
}




