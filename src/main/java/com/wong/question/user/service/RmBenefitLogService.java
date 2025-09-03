package com.wong.question.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wong.question.user.entity.RmBenefitLogEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wong.question.user.entity.dto.ConsumeUserInfoDTO;
import com.wong.question.user.entity.dto.RmBenefitsLogDTO;

import java.util.List;

/**
* @author 86184
* @description 针对表【rm_benefit_log(分销收益记录表)】的数据库操作Service
* @createDate 2025-08-28 16:38:38
*/
public interface RmBenefitLogService extends IService<RmBenefitLogEntity> {

    IPage<RmBenefitsLogDTO> getBenefitLogPage(Integer pageNo, Integer pageSize,
                                              String fromUserName, String benefitUserName, Integer status);

    /**
     * 查询指定用户的推广明细（支持分页和按分销层级分类）
     * @param benefitUserId 推广人用户ID
     * @param level 分销层级（1=一级，2=二级，null=全部）
     * @param pageNo 页码
     * @param pageSize 每页数量
     */
    IPage<ConsumeUserInfoDTO> getBenefitDetailByUserId(String benefitUserId, Integer level, int pageNo, int pageSize);
}


