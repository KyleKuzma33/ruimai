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
 * 用户收益汇总表
 * @TableName rm_user_benefit_summary
 */
@TableName(value ="rm_user_benefit_summary")
@Data
public class RmUserBenefitSummaryEntity {
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 总收益
     */
    @TableField(value = "total_benefit")
    private BigDecimal totalBenefit;

    /**
     * 一级收益累计
     */
    @TableField(value = "level1_benefit")
    private BigDecimal level1Benefit;

    /**
     * 二级收益累计
     */
    @TableField(value = "level2_benefit")
    private BigDecimal level2Benefit;

    /**
     * 可提现余额
     */
    @TableField(value = "withdrawable_balance")
    private BigDecimal withdrawableBalance;

    /**
     * 冻结金额（待结算/审核中）
     */
    @TableField(value = "frozen_balance")
    private BigDecimal frozenBalance;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 最后更新时间
     */
    @TableField(value = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}