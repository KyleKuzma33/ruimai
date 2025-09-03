package com.wong.question.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 轮播图表
 * @TableName rm_carousel_chart
 */
@ApiModel(value = "轮播图实体", description = "轮播图信息")
@TableName(value ="rm_carousel_chart")
@Data
public class RmCarouselChartEntity {
    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 轮播图id
     */
    @ApiModelProperty(value = "轮播图唯一标识ID")
    @TableField(value = "carousel_chart_id")
    private String carouselChartId;

    /**
     * 轮播图标题
     */
    @ApiModelProperty(value = "轮播图展示标题")
    @TableField(value = "carousel_chart_title")
    private String carouselChartTitle;

    /**
     * 轮播图图片
     */
    @ApiModelProperty(value = "轮播图图片URL路径")
    @TableField(value = "carousel_chart_img")
    private String carouselChartImg;

    /**
     * 轮播排序
     */
    @ApiModelProperty(value = "轮播图展示顺序，数值越小越靠前")
    @TableField(value = "carousel_chart_sort")
    private Integer carouselChartSort;

    /**
     * 状态：1正常，0禁用
     */
    @ApiModelProperty(value = "轮播图状态：1-正常展示，0-禁用不展示")
    @TableField(value = "carousel_status")
    private Integer carouselStatus;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "轮播图记录创建时间")
    @TableField(value = "creation_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creationTime;
}