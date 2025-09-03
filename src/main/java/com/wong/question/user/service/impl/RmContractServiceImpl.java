package com.wong.question.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wong.question.user.entity.RmContractEntity;
import com.wong.question.user.service.RmContractService;
import com.wong.question.user.mapper.RmContractMapper;
import org.springframework.stereotype.Service;

/**
* @author 86184
* @description 针对表【rm_contract(合同表)】的数据库操作Service实现
* @createDate 2025-08-21 21:59:58
*/
@Service
public class RmContractServiceImpl extends ServiceImpl<RmContractMapper, RmContractEntity>
    implements RmContractService{

    @Override
    public RmContractEntity queryNewContract() {
        LambdaQueryWrapper<RmContractEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RmContractEntity::getContractStatus,1).orderByDesc(RmContractEntity::getCreationTime).last("limit 1");
        return this.getOne(wrapper);
    }
}




