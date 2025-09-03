package com.wong.question.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 分销收益记录表
 * @TableName rm_benefit_log
 */
@TableName(value ="rm_benefit_log")
@Data
public class RmBenefitLogEntity {
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单ID
     */
    @TableField(value = "order_id")
    private Long orderId;

    /**
     * 消费用户ID
     */
    @TableField(value = "from_user_id")
    private String fromUserId;

    /**
     * 获益用户ID
     */
    @TableField(value = "benefit_user_id")
    private String benefitUserId;

    /**
     * 分销层级：1=一级，2=二级
     */
    @TableField(value = "level")
    private Integer level;

    /**
     * 收益金额
     */
    @TableField(value = "amount")
    private BigDecimal amount;

    /**
     * 状态：1=有效，0=已回滚
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}