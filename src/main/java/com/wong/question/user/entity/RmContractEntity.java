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
 * 合同表
 * @TableName rm_contract
 */
@ApiModel(value = "RmContractEntity", description = "合同表实体")
@TableName(value ="rm_contract")
@Data
public class RmContractEntity {
    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID", example = "1")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 合同id
     */
    @ApiModelProperty(value = "合同唯一编号", example = "HT20250822001")
    @TableField(value = "contract_id")
    private String contractId;

    /**
     * 合同内容
     */
    @ApiModelProperty(value = "合同内容", example = "这是一个合同的详细内容...")
    @TableField(value = "contract_content")
    private String contractContent;

    /**
     * 合同类型（h5,第三方）
     */
    @ApiModelProperty(value = "合同类型:  html、https", example = "html")
    @TableField(value = "contract_type")
    private String contractType;

    /**
     * 状态：1正常，0禁用
     */
    @ApiModelProperty(value = "状态：1正常，0禁用", example = "1")
    @TableField(value = "contract_status")
    private Integer contractStatus;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2025-08-22 12:00:00")
    @TableField(value = "creation_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creationTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", example = "2025-08-22 13:30:00")
    @TableField(value = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
