package com.wong.question.user.service;

import com.wong.question.user.entity.RmUserContractEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wong.question.user.entity.dto.SignContractDTO;

/**
* @author 86184
* @description 针对表【rm_user_contract(用户合同签署记录表)】的数据库操作Service
* @createDate 2025-08-31 10:44:51
*/
public interface RmUserContractService extends IService<RmUserContractEntity> {
        RmUserContractEntity addContracts(SignContractDTO signContractDTO);
}
