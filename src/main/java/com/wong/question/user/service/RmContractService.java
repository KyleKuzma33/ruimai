package com.wong.question.user.service;

import com.wong.question.user.entity.RmContractEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 86184
* @description 针对表【rm_contract(合同表)】的数据库操作Service
* @createDate 2025-08-21 21:59:58
*/
public interface RmContractService extends IService<RmContractEntity> {
      RmContractEntity queryNewContract();
}
