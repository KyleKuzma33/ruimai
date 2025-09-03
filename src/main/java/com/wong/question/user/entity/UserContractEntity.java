package com.wong.question.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户-合同关联表
 * @TableName user_contract
 */
@TableName(value ="user_contract")
@Data
public class UserContractEntity {
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("ID")
    private Long id;

    /**
     * 用户表ID
     */
    @TableField(value = "user_id")
    @ApiModelProperty("用户ID")
    private Long userId;

    /**
     * 合同表ID
     */
    @TableField(value = "contract_id")
    @ApiModelProperty("合同ID")
    private String contractId;

    /**
     * 签署时间
     */
    @TableField(value = "signed_time")
    @ApiModelProperty("签署时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date signedTime;

    /**
     * 签署状态：1已签署，0未签署
     */
    @TableField(value = "status")
    @ApiModelProperty("签署状态：1已签署，0未签署")
    private Integer status;
}