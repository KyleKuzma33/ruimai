package com.wong.question.user.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;



//查看收益订单的实体类
@Data
public class RmBenefitsLogDTO {
    private Long id;
    private Long orderId;

    private String fromUserId;
    private String fromUserName;
    private String fromUserPhone;

    private String benefitUserId;
    private String benefitUserName;
    private String benefitUserPhone;

    private Integer level;
    private BigDecimal amount;   // 需要 import java.math.BigDecimal
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
