package com.wong.question.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wong.question.user.entity.RmBenefitsEntity;
import com.wong.question.user.mapper.RmBenefitsMapper;
import com.wong.question.user.service.RmBenefitsService;
import org.springframework.stereotype.Service;

@Service
public class RmBenefitsServiceImpl extends ServiceImpl<RmBenefitsMapper, RmBenefitsEntity>
        implements RmBenefitsService {

    private final RmBenefitsMapper benefitsMapper;

    public RmBenefitsServiceImpl(RmBenefitsMapper benefitsMapper) {
        this.benefitsMapper = benefitsMapper;
    }


}
