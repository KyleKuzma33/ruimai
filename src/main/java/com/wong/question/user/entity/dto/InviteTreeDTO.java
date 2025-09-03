package com.wong.question.user.entity.dto;

import com.wong.question.user.entity.RmUserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


//用户关联树dao
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InviteTreeDTO {
    private List<RmUserEntity> children; // 邀请树
    private int totalCount; // 总人数
}
