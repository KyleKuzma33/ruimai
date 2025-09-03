package com.wong.question.user.service;

import com.wong.question.user.entity.RmUserBenefitSummaryEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 86184
* @description 针对表【rm_user_benefit_summary(用户收益汇总表)】的数据库操作Service
* @createDate 2025-08-29 09:38:16
*/
public interface RmUserBenefitSummaryService extends IService<RmUserBenefitSummaryEntity> {
    List<RmUserBenefitSummaryEntity> getByUserId(String userId);
}
