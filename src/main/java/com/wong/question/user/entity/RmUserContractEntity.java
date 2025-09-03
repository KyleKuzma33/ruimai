package com.wong.question.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 用户合同签署记录表
 * @TableName rm_user_contract
 */
@TableName(value ="rm_user_contract")
@Data
public class RmUserContractEntity {
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户电话
     */
    @TableField(value = "user_phone")
    private String userPhone;

    /**
     * 用户名
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 合同ID
     */
    @TableField(value = "contract_id")
    private String contractId;

    /**
     * 订单id
     */
    @TableField(value = "order_id")
    private int orderId;

    /**
     * 用户签名后的合同存储路径（PDF/图片/HTML）
     */
    @TableField(value = "signed_contract_url")
    private String signedContractUrl;

    /**
     * 用户签名图片（可选）
     */
    @TableField(value = "signature_img")
    private String signatureImg;

    /**
     * 签署时间
     */
    @TableField(value = "sign_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date signTime;

    /**
     * 状态：1已签署，0撤销/失效
     */
    @TableField(value = "status")
    private Integer status;
}