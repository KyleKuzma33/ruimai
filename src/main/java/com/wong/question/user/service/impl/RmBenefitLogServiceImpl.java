package com.wong.question.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wong.question.user.entity.RmBenefitLogEntity;
import com.wong.question.user.entity.dto.ConsumeUserInfoDTO;
import com.wong.question.user.entity.dto.RmBenefitsLogDTO;
import com.wong.question.user.service.RmBenefitLogService;
import com.wong.question.user.mapper.RmBenefitLogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 86184
* @description 针对表【rm_benefit_log(分销收益记录表)】的数据库操作Service实现
* @createDate 2025-08-28 16:38:38
*/
@Service
public class RmBenefitLogServiceImpl extends ServiceImpl<RmBenefitLogMapper, RmBenefitLogEntity>
    implements RmBenefitLogService{
    @Resource
    private RmBenefitLogMapper rmBenefitLogMapper;
    @Override
    public IPage<RmBenefitsLogDTO> getBenefitLogPage(Integer pageNo, Integer pageSize, String fromUserName, String benefitUserName, Integer status) {
        Page<RmBenefitsLogDTO> page = new Page<>(pageNo, pageSize);
        return this.baseMapper.queryBenefitLogPage(page, fromUserName, benefitUserName, status);
    }

    @Override
    public IPage<ConsumeUserInfoDTO> getBenefitDetailByUserId(String benefitUserId, Integer level, int pageNo, int pageSize) {
        Page<ConsumeUserInfoDTO> page = new Page<>(pageNo, pageSize);
        return rmBenefitLogMapper.getBenefitDetailByUserId(page, benefitUserId, level);
    }
}




