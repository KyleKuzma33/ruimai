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
 * 店铺表
 * @TableName rm_shop
 */
@ApiModel(value = "店铺实体", description = "用于存储店铺的基本信息")
@TableName(value ="rm_shop")
@Data
public class RmShopEntity {
    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID", example = "1")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺唯一标识", example = "shop_20250822")
    @TableField(value = "shop_id")
    private String shopId;

    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称", example = "小米旗舰店")
    @TableField(value = "shop_name")
    private String shopName;

    /**
     * 店铺收益
     */
    @ApiModelProperty(value = "店铺累计收益", example = "12890.50")
    @TableField(value = "shop_benefits")
    private BigDecimal shopBenefits;

    /**
     * 状态：1正常，0禁用
     */
    @ApiModelProperty(value = "状态：1正常，0禁用", example = "1")
    @TableField(value = "shop_status")
    private Integer shopStatus;

    /**
     * 店铺关联用户id
     */
    @ApiModelProperty(value = "关联的用户ID", example = "10001")
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2025-08-22 14:30:00")
    @TableField(value = "creation_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creationTime;
}
