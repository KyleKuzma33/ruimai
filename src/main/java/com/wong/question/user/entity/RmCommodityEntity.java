package com.wong.question.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品表
 * @TableName rm_commodity
 */
@ApiModel(value = "RmCommodityEntity", description = "商品实体")
@TableName(value ="rm_commodity")
@Data
public class RmCommodityEntity {
    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品ID", example = "1")
    @TableId(value = "commodity_id", type = IdType.AUTO)
    private Long commodityId;

    /**
     * 商品名字
     */
    @ApiModelProperty(value = "商品名称", example = "苹果手机")
    @TableField(value = "commodity_name")
    private String commodityName;

    /**
     * 商品价格
     */
    @ApiModelProperty(value = "商品价格", example = "5999.99")
    @TableField(value = "commodity_price")
    private BigDecimal commodityPrice;

    /**
     * 状态：1正常，0禁用
     */
    @ApiModelProperty(value = "商品状态：1正常，0禁用", example = "1")
    @TableField(value = "commodity_status")
    private Integer commodityStatus;

    /**
     * 商品图片
     */
    @ApiModelProperty(value = "商品图片URL", example = "http://example.com/img.png")
    @TableField(value = "commodity_img")
    private String commodityImg;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2025-08-22T10:00:00")
    @TableField(value = "creation_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creationTime;
}
