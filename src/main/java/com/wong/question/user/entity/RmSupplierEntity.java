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
 * 供应商申请表
 * @TableName rm_supplier
 */
@ApiModel(value = "供应商申请实体", description = "用于记录用户提交的供应商申请信息")
@TableName(value ="rm_supplier")
@Data
public class RmSupplierEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户id")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty("小程序唯一标识")
    @TableField("user_openid")
    private String userOpenid;

    @ApiModelProperty("用户姓名")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty("用户密码")
    @TableField("user_pwd")
    private String userPwd;

    @ApiModelProperty("用户电话")
    @TableField("user_phone")
    private String userPhone;

    @ApiModelProperty("供应商: 1是，0不是")
    @TableField("is_supplier")
    private Integer isSupplier;

    @ApiModelProperty("邀请人ID，可空")
    @TableField("invite_user_id")
    private Long inviteUserId;

    @ApiModelProperty("状态：1正常，0禁用")
    @TableField("user_status")
    private Integer userStatus;

    @ApiModelProperty("店铺收益")
    @TableField("shop_benefits")
    private BigDecimal shopBenefits;

    @ApiModelProperty("推广收益")
    @TableField("promotion_benefits")
    private BigDecimal promotionBenefits;

    @ApiModelProperty("创建时间")
    @TableField("creation_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creationTime;
}
