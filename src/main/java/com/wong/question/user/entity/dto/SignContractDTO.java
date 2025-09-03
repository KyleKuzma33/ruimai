package com.wong.question.user.entity.dto;

import lombok.Data;

//合同签署实体类
@Data
public class SignContractDTO {
    private Integer orderId;
    private String contractId;
    private String signContent; // base64
}
