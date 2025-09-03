package com.wong.question.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

/**
 * 收益表
 * @TableName rm_benefits
 */
@ApiModel(value = "收益实体", description = "用于记录用户推广收益信息")
@TableName(value ="rm_benefits")
@Data
public class RmBenefitsEntity {
    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID", example = "1")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 收益id
     */
    @ApiModelProperty(value = "收益唯一标识", example = "benefits_20250822")
    @TableField(value = "benefits_id")
    private String benefitsId;

    /**
     * 推广用户id
     */
    @ApiModelProperty(value = "推广用户ID", example = "1001")
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 推广收益用户id
     */
    @ApiModelProperty(value = "收益用户ID", example = "1002")
    @TableField(value = "benefits_user_id")
    private Long benefitsUserId;

    /**
     * 收益类型（1：一级  2：二级）
     */
    @ApiModelProperty(value = "收益类型（1：一级 2：二级）", example = "1")
    @TableField(value = "benefits_type")
    private Integer benefitsType;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2025-08-22 14:30:00")
    @TableField(value = "creation_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creationTime;
}
