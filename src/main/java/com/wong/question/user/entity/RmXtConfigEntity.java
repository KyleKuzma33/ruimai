package com.wong.question.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @TableName rm_xt_config
 */
@TableName(value ="rm_xt_config")
@ApiModel(value = "RmXtConfigEntity", description = "系统配置表")
@Data
public class RmXtConfigEntity {
    @ApiModelProperty(value = "服务商配置ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "初级服务商满足条件(默认6)")
    @TableField("primary_service")
    private Integer primaryService = 6;

    @ApiModelProperty(value = "中级服务商满足条件（默认20）")
    @TableField("intermediate_service")
    private Integer intermediateService = 20;

    @ApiModelProperty(value = "高级服务商满足条件（默认50）")
    @TableField("senior_senior")
    private Integer seniorSenior = 50;

    @ApiModelProperty(value = "初级直推店铺收益比例（默认0.1）")
    @TableField("primary_benefits_one")
    private BigDecimal primaryBenefitsOne = BigDecimal.valueOf(0.0);

    @ApiModelProperty(value = "中级直推店铺收益比例（默认0.15）")
    @TableField("intermediate_benefits_one")
    private BigDecimal intermediateBenefitsOne = BigDecimal.valueOf(0.0);

    @ApiModelProperty(value = "高级直推店铺收益比例（默认0.2）")
    @TableField("senior_benefits_one")
    private BigDecimal seniorBenefitsOne = BigDecimal.valueOf(0.0);

    @ApiModelProperty(value = "初级间推店铺收益比例（默认0.1）")
    @TableField("primary_benefits_two")
    private BigDecimal primaryBenefitsTwo = BigDecimal.valueOf(0.0);

    @ApiModelProperty(value = "中级间推店铺收益比例（默认0.2）")
    @TableField("intermediate_benefits_two")
    private BigDecimal intermediateBenefitsTwo = BigDecimal.valueOf(0.0);

    @ApiModelProperty(value = "高级间推店铺收益比例（默认0.3）")
    @TableField("senior_benefits_two")
    private BigDecimal seniorBenefitsTwo = BigDecimal.valueOf(0.0);

    @ApiModelProperty(value = "一级推广收益（默认0.1）")
    @TableField("extension_one")
    private BigDecimal extensionOne = BigDecimal.valueOf(0.0);

    @ApiModelProperty(value = "二级推广收益（默认0.05）")
    @TableField("extension_two")
    private BigDecimal extensionTwo = BigDecimal.valueOf(0.0);
    @ApiModelProperty(value = "创建人")
    @TableField(value = "create_user_id", fill = FieldFill.INSERT)
    private String createUserId;

    @ApiModelProperty(value = "修改人")
    @TableField(value = "update_user_id", fill = FieldFill.INSERT_UPDATE)
    private String updateUser_id;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}