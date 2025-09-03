package com.wong.question.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wong.question.user.controller.UserContractController;
import com.wong.question.user.entity.RmUserContractEntity;
import com.wong.question.user.entity.UserContractEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wong.question.user.entity.dto.SignContractDTO;

/**
* @author 86184
* @description 针对表【user_contract(用户-合同关联表)】的数据库操作Service
* @createDate 2025-08-27 18:05:32
*/
public interface UserContractService extends IService<UserContractEntity> {

}
