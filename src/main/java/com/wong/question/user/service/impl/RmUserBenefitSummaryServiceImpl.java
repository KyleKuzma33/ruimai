package com.wong.question.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wong.question.user.entity.RmUserBenefitSummaryEntity;
import com.wong.question.user.service.RmUserBenefitSummaryService;
import com.wong.question.user.mapper.RmUserBenefitSummaryMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
* @author 86184
* @description 针对表【rm_user_benefit_summary(用户收益汇总表)】的数据库操作Service实现
* @createDate 2025-08-29 09:38:16
*/
@Service
public class RmUserBenefitSummaryServiceImpl extends ServiceImpl<RmUserBenefitSummaryMapper, RmUserBenefitSummaryEntity>
    implements RmUserBenefitSummaryService{

    @Override
    public List<RmUserBenefitSummaryEntity> getByUserId(String userId) {
        return this.list(new LambdaQueryWrapper<RmUserBenefitSummaryEntity>()
                .eq(RmUserBenefitSummaryEntity::getUserId, userId));
    }
}




