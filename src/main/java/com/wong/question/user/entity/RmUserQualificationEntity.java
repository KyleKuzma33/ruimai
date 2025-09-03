package com.wong.question.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 用户资质表
 * @TableName rm_user_qualification
 */
@TableName(value ="rm_user_qualification")
@Data
public class RmUserQualificationEntity {
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 关联用户ID
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 用户手机号
     */
    @TableField(value = "user_phone")
    private String userPhone;

    /**
     * 用户姓名
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 营业执照照片路径
     */
    @TableField(value = "business_license")
    private String businessLicense;

    /**
     * 法人身份证正面照片路径
     */
    @TableField(value = "legal_id_front")
    private String legalIdFront;

    /**
     * 法人身份证反面照片路径
     */
    @TableField(value = "legal_id_back")
    private String legalIdBack;

    /**
     * 资质上传审核状态
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 审核备注/驳回原因
     */
    @TableField(value = "review_remark")
    private String reviewRemark;

    /**
     * 审核人ID
     */
    @TableField(value = "reviewer_id")
    private String reviewerId;

    /**
     * 订单id
     */
    @TableField(value = "order_id")
    private Integer orderId;

    /**
     * 审核时间
     */
    @TableField(value = "review_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reviewTime;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}