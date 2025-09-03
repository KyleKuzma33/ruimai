package com.wong.question.user.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 订单返回实体类分装
 * */
@Data
public class OrderUserDTO {
    private Long id;
    private String userId;
    private String userName;      // 用户姓名
    private String userPhone;     // 用户电话
    private Integer status;
    private Long commodityId;
    private Integer commodityNumber;
    private BigDecimal amount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creationTime;
}
