package com.wong.question.user.entity.dto;

import lombok.Data;


//资质审核接受实体类
@Data
public class QualificationReviewDTO {
    private Integer id;           // 用户资质ID
    private String reviewRemark; // 审核备注
    private Integer status;      // 审核状态（0未通过，1通过）
}
