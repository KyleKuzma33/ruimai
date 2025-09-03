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
 * 
 * @TableName rm_user_relation
 */
@TableName(value ="rm_user_relation")
@Data
@ApiModel(value = "RmUserRelationEntity", description = "瑞麦服务商等级表")
public class RmUserRelationEntity {
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键ID")
    private Long id;

    /**
     * 当前用户ID
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "当前用户ID")

    private String userId;

    /**
     * 上级用户ID（推荐人ID）
     */
    @TableField(value = "parent_id")
    @ApiModelProperty(value = "推荐人ID")

    private String parentId;

    /**
     * 关系层级：1=直推，2=间推
     */
    @TableField(value = "relation_level")
    @ApiModelProperty(value = "关系层级：1=直推，2=间推")

    private Integer relationLevel;

    /**
     * 绑定时推荐人的服务商等级
     */
    @TableField(value = "supplier_level")
    @ApiModelProperty(value = "绑定时推荐人的服务商等级")

    private Integer supplierLevel;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}