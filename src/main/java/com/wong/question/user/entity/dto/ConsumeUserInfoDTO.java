package com.wong.question.user.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

//查看指定用户下他二级一级用户推广收益的实体类
@Data
public class ConsumeUserInfoDTO {
    private String fromUserId;        // 消费用户ID
    private String fromUserName;      // 消费用户姓名
    private Integer level;            // 分销层级（1=一级，2=二级）
    private BigDecimal consumeAmount; // 消费金额
    private BigDecimal benefitAmount; // 给推广人带来的收益
    private Long orderId;             // 订单ID
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date payTime;             // 支付时间
}
