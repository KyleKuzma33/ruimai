package com.wong.question.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wong.question.user.entity.RmBenefitLogEntity;
import com.wong.question.user.entity.RmCommodityEntity;
import com.wong.question.user.entity.RmOrderEntity;
import com.wong.question.user.entity.RmUserEntity;
import com.wong.question.user.entity.dto.OrderUserDTO;
import com.wong.question.user.service.RmBenefitLogService;
import com.wong.question.user.service.RmCommodityService;
import com.wong.question.user.service.RmOrderService;
import com.wong.question.user.mapper.RmOrderMapper;
import com.wong.question.user.service.RmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author 86184
* @description 针对表【rm_order】的数据库操作Service实现
* @createDate 2025-08-28 15:56:24
*/
@Service
public class RmOrderServiceImpl extends ServiceImpl<RmOrderMapper, RmOrderEntity>
        implements RmOrderService {
    @Resource
    private RmOrderMapper rmOrderMapper;
    @Autowired
    private RmUserService rmUserService;
    @Autowired
    private RmCommodityService rmCommodityService;
    @Autowired
    private RmBenefitLogService rmBenefitLogService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RmOrderEntity addOrder(RmOrderEntity order) {
        // 基础参数校验
        if (order.getCommodityId() == null || order.getCommodityNumber() == null || order.getUserId() == null) {
            throw new RuntimeException("订单参数异常");
        }
        if (order.getCommodityNumber() <= 0) {
            throw new RuntimeException("购买数量必须大于0");
        }
        // 校验用户是否存在
        RmUserEntity user = rmUserService.getOne(
                new LambdaQueryWrapper<RmUserEntity>().eq(RmUserEntity::getUserId, order.getUserId()));
        if (user == null) throw new RuntimeException("用户不存在");
        // 校验商品是否存在
        RmCommodityEntity commodity = rmCommodityService.getById(order.getCommodityId());
        if (commodity == null) throw new RuntimeException("商品不存在");
        // 计算订单金额
        BigDecimal amount = commodity.getCommodityPrice().multiply(BigDecimal.valueOf(order.getCommodityNumber()));
        order.setAmount(amount);
        order.setCreationTime(new Date());
        order.setStatus(1); // 已支付
        this.save(order);
        rmCommodityService.updateById(commodity);
        // 分销收益
        distributeBenefits(order, user);
        return order;
    }


    private void distributeBenefits(RmOrderEntity order, RmUserEntity buyer) {
        //获取订单支付总额
        BigDecimal amount = order.getAmount();
        //一级代理提成
        final BigDecimal LEVEL_ONE_RATE = new BigDecimal("0.10");
        //二级代理提成
        final BigDecimal LEVEL_TWO_RATE = new BigDecimal("0.025");
        //查看用户通过Id
        if (buyer.getInviteUserId() != null) {
            RmUserEntity levelOne = rmUserService.getOne(
                    new LambdaQueryWrapper<RmUserEntity>().eq(RmUserEntity::getUserId, buyer.getInviteUserId()));
            if (levelOne != null) {
                //一级代理提成
                BigDecimal benefitOne = amount.multiply(LEVEL_ONE_RATE);
                levelOne.setPromotionBenefits(levelOne.getPromotionBenefits().add(benefitOne));
                rmUserService.updateById(levelOne);

                // 保存收益记录
                saveBenefitLog(order, buyer.getUserId(), levelOne.getUserId(), 1, benefitOne);

                // 二级分销
                if (levelOne.getInviteUserId() != null) {
                    RmUserEntity levelTwo = rmUserService.getOne(
                            new LambdaQueryWrapper<RmUserEntity>().eq(RmUserEntity::getUserId, levelOne.getInviteUserId()));
                    if (levelTwo != null) {
                        BigDecimal benefitTwo = amount.multiply(LEVEL_TWO_RATE);
                        levelTwo.setPromotionBenefits(levelTwo.getPromotionBenefits().add(benefitTwo));
                        rmUserService.updateById(levelTwo);

                        saveBenefitLog(order, buyer.getUserId(), levelTwo.getUserId(), 2, benefitTwo);
                    }
                }
            }
        }
    }

    private void saveBenefitLog(RmOrderEntity order, String fromUserId, String benefitUserId, int level, BigDecimal amount) {
        RmBenefitLogEntity log = new RmBenefitLogEntity();
        log.setOrderId(order.getId());
        log.setFromUserId(fromUserId);
        log.setBenefitUserId(benefitUserId);
        log.setLevel(level);
        log.setAmount(amount);
        log.setStatus(1);
        log.setCreateTime(new Date());
        rmBenefitLogService.save(log);
    }

    @Override
    @Transactional
    public void refundOrder(Long orderId) {
        RmOrderEntity order = this.getById(orderId);
        if (order == null) throw new RuntimeException("订单不存在");
        if (order.getStatus() == 2) throw new RuntimeException("订单已退款");

        List<RmBenefitLogEntity> logs = rmBenefitLogService.list(
                new LambdaQueryWrapper<RmBenefitLogEntity>()
                        .eq(RmBenefitLogEntity::getOrderId, orderId)
                        .eq(RmBenefitLogEntity::getStatus, 1)
        );

        for (RmBenefitLogEntity log : logs) {
            RmUserEntity benefitUser = rmUserService.getOne(
                    new LambdaQueryWrapper<RmUserEntity>().eq(RmUserEntity::getUserId, log.getBenefitUserId()));
            if (benefitUser != null) {
                benefitUser.setPromotionBenefits(
                        benefitUser.getPromotionBenefits().subtract(log.getAmount()));
                rmUserService.updateById(benefitUser);

                log.setStatus(0);
                rmBenefitLogService.updateById(log);
            }
        }

        order.setStatus(2);
        this.updateById(order);
    }

    @Override
    public Page<OrderUserDTO> getUserOrders(String userId, Integer status, Date startTime, Date endTime, int pageNum, int pageSize) {
        Page<OrderUserDTO> page = new Page<>(pageNum, pageSize);

        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("status", status);
        params.put("startTime", startTime);
        params.put("endTime", endTime);

        return rmOrderMapper.selectOrdersWithUser(page, params);
    }
}




