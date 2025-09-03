package com.wong.question.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wong.question.user.entity.RmUserContractEntity;
import com.wong.question.user.entity.UserContractEntity;
import com.wong.question.user.entity.dto.SignContractDTO;
import com.wong.question.user.service.RmUserContractService;
import com.wong.question.user.service.UserContractService;
import com.wong.question.user.mapper.UserContractMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Date;

/**
* @author 86184
* @description 针对表【user_contract(用户-合同关联表)】的数据库操作Service实现
* @createDate 2025-08-27 18:05:32
*/
@Service
public class UserContractServiceImpl extends ServiceImpl<UserContractMapper, UserContractEntity>
    implements UserContractService{

}




