package com.wong.question.user.controller;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wong.question.user.entity.RmOrderEntity;
import com.wong.question.user.entity.RmUserEntity;
import com.wong.question.user.service.RmOrderService;
import com.wong.question.user.service.RmUserService;
import com.wong.question.utils.R;
import com.wong.question.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "瑞麦模拟支付接口")
@RestController
@RequestMapping("/rm/pay")
public class testPayController {
    @Autowired
    private RmOrderService rmOrderService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RmUserService rmUserService;

    @ApiOperation("创建订单")
    @PostMapping("/create")
    public R createOrder(@RequestBody RmOrderEntity order,@RequestHeader("Authorization") String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return R.error("未传递 token");
        }
        String token = authorization.substring(7); // 去掉 "Bearer " 前缀
        // 从 redis 获取
        String redisKey = "login/" + token;
        String redisValue = (String) redisUtil.get(redisKey);
        if (redisValue == null) {
            return R.error("token 无效或已过期");
        }
        JSONObject obj = JSONObject.parseObject(redisValue);
        String userId = obj.getString("userId");
        // 查询数据库，保证用户是最新数据
        // 根据 userId 字段查询用户
        RmUserEntity user = rmUserService.getOne(
                new LambdaQueryWrapper<RmUserEntity>().eq(RmUserEntity::getUserId, userId)
        );
        if (user == null) {
            return R.error("用户不存在");
        }
        order.setUserId(userId);
        RmOrderEntity orderEntity = rmOrderService.addOrder(order);
        if (orderEntity == null) return R.error("创建订单失败");
        return R.success("创建订单成功",orderEntity);
    }

    @PostMapping("/refund/{orderId}")
    public R refundOrder(@PathVariable Long orderId) {
        rmOrderService.refundOrder(orderId);
        return  R.success("退款成功");
    }
}
