package com.wong.question.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 佣金表
 * @TableName rm_commission
 */
@ApiModel(value = "佣金实体", description = "记录用户佣金信息")
@TableName(value ="rm_commission")
@Data
public class RmCommissionEntity {

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID", example = "1")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 获得佣金用户id
     */
    @ApiModelProperty(value = "获得佣金用户ID", example = "1001")
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 触发用户ID
     */
    @ApiModelProperty(value = "触发佣金用户ID", example = "1002")
    @TableField(value = "source_user_id")
    private Long sourceUserId;

    /**
     * 佣金类型：1直推  2间推
     */
    @ApiModelProperty(value = "佣金类型：1直推，2间推", example = "1")
    @TableField(value = "commission_type")
    private Integer commissionType;

    /**
     * 佣金金额
     */
    @ApiModelProperty(value = "佣金金额", example = "100.50")
    @TableField(value = "amount")
    private BigDecimal amount;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2025-08-22 14:30:00")
    @TableField(value = "creation_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creationTime;
}
