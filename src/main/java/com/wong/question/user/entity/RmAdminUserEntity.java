package com.wong.question.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 管理员用户表
 * @TableName rm_admin_user
 */
@TableName(value ="rm_admin_user")
@ApiModel(value = "RmAdminUserEntity", description = "管理员用户表")
@Data
public class RmAdminUserEntity {
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键ID")
    private Long id;

    /**
     * 用户名ID
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户ID")
    private String userId;

    /**
     * 用户名
     */
    @TableField(value = "user_name")
    @ApiModelProperty(value = "用户名")
    private String userName;

    /**
     * 用户电话
     */
    @TableField(value = "user_mobile")
    @ApiModelProperty(value = "用户电话")
    private String userMobile;

    /**
     * 用户登录密码
     */
    @TableField(value = "user_pwd")
    @ApiModelProperty(value = "用户登录密码")
    private String userPwd;

    /**
     * 创建时间
     */
    @TableField(value = "creation_time")
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creationTime;
}
