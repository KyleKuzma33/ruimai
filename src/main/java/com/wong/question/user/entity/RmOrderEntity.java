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
 * 
 * @TableName rm_order
 */
@TableName(value ="rm_order")
@Data
public class RmOrderEntity {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 消费用户ID
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 订单状态：1=已支付，2=已退款'
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 商品ID
     */
    @TableField(value = "commodity_id")
    private Long commodityId;

    /**
     * 商品数量
     */
    @TableField(value = "commodity_number")
    private Integer commodityNumber;

    /**
     * 消费金额
     */
    @TableField(value = "amount")
    private BigDecimal amount;

    /**
     * 
     */
    @TableField(value = "creation_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")

    private Date creationTime;
}