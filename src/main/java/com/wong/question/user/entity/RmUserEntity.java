package com.wong.question.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 用户表
 * @TableName rm_user
 */
@TableName(value ="rm_user")
@Data
public class RmUserEntity {
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 小程序唯一标识
     */
    @TableField(value = "user_openid")
    private String userOpenid;

    /**
     * 用户姓名
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 用户密码
     */
    @TableField(value = "user_pwd")
    private String userPwd;

    /**
     * 用户电话
     */
    @TableField(value = "user_phone")
    private String userPhone;

    /**
     * 供应商: 1是，0不是
     */
    @TableField(value = "is_supplier")
    private Integer isSupplier;

    /**
     * 邀请人ID，可空
     */
    @TableField(value = "invite_user_id")
    private String inviteUserId;

    /**
     * 状态：1正常，0禁用
     */
    @TableField(value = "user_status")
    private Integer userStatus;

    /**
     * 店铺收益
     */
    @TableField(value = "shop_benefits")
    private BigDecimal shopBenefits;

    /**
     * 推广
     */
    @TableField(value = "promotion_benefits")
    private BigDecimal promotionBenefits;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "creation_time")
    private Date creationTime;

    @TableField(exist = false) // MyBatis-Plus 不会映射到数据库
    private List<RmUserEntity> children;
}