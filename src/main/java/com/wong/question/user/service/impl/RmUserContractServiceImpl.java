package com.wong.question.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wong.question.user.entity.RmOrderEntity;
import com.wong.question.user.entity.RmUserContractEntity;
import com.wong.question.user.entity.RmUserEntity;
import com.wong.question.user.entity.dto.SignContractDTO;
import com.wong.question.user.service.RmOrderService;
import com.wong.question.user.service.RmUserContractService;
import com.wong.question.user.mapper.RmUserContractMapper;
import com.wong.question.user.service.RmUserService;
import com.wong.question.user.service.UserService;
import com.wong.question.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

/**
* @author 86184
* @description 针对表【rm_user_contract(用户合同签署记录表)】的数据库操作Service实现
* @createDate 2025-08-31 10:44:51
*/
@Service
public class RmUserContractServiceImpl extends ServiceImpl<RmUserContractMapper, RmUserContractEntity>
    implements RmUserContractService{

    @Autowired
    private RmUserService rmUserService;

    @Autowired
    private RmOrderService rmOrderService;



    @Override
    public RmUserContractEntity addContracts(SignContractDTO signContractDTO) {
        String uid = UserContext.getUserId();
        RmUserEntity userEntity = rmUserService.getOne(
                new LambdaQueryWrapper<RmUserEntity>().eq(RmUserEntity::getUserId, uid)
        );
        if (userEntity == null) return null;

        String u_phone = userEntity.getUserPhone();
        String u_name = userEntity.getUserName();
        Integer orderId = signContractDTO.getOrderId();
        String contractId = signContractDTO.getContractId();

        // 1. 检查订单是否存在且属于当前用户
        RmOrderEntity order = rmOrderService.getOne(
                new LambdaQueryWrapper<RmOrderEntity>()
                        .eq(RmOrderEntity::getId, orderId)
                        .eq(RmOrderEntity::getUserId, uid)
        );

        if (order == null) {
            throw new RuntimeException("订单不存在或不属于当前用户，无法签署合同");
        }

        // 2. 检查是否已经签署过该订单合同
        LambdaQueryWrapper<RmUserContractEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RmUserContractEntity::getUserId, uid)
                .eq(RmUserContractEntity::getOrderId, orderId);
        RmUserContractEntity existing = this.getOne(queryWrapper);

        if (existing != null) {
            // 已签署
            return existing;
        }

        // 3. 创建新的合同记录
        RmUserContractEntity userContractEntity = new RmUserContractEntity();
        userContractEntity.setUserPhone(u_phone);
        userContractEntity.setUserName(u_name);
        userContractEntity.setUserId(uid);
        userContractEntity.setOrderId(orderId);
        userContractEntity.setContractId(contractId);
        userContractEntity.setStatus(1); // 已签署状态
        userContractEntity.setSignedContractUrl(signContractDTO.getSignContent());
        userContractEntity.setSignTime(new Date());

        boolean isSave = this.save(userContractEntity);
        return isSave ? userContractEntity : null;
    }



}




