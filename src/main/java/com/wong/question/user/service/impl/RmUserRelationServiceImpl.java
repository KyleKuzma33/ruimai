package com.wong.question.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wong.question.user.entity.RmUserRelationEntity;
import com.wong.question.user.service.RmUserRelationService;
import com.wong.question.user.mapper.RmUserRelationMapper;
import org.springframework.stereotype.Service;

/**
* @author 86184
* @description 针对表【rm_user_relation】的数据库操作Service实现
* @createDate 2025-09-01 09:10:18
*/
@Service
public class RmUserRelationServiceImpl extends ServiceImpl<RmUserRelationMapper, RmUserRelationEntity>
    implements RmUserRelationService{
    /**
     * 根据用户 ParentID 查询最新关系
     */
    @Override
    public RmUserRelationEntity getLatestByParentId(String parent_id) {
         return lambdaQuery()
                .eq(RmUserRelationEntity::getParentId, parent_id)
                .orderByDesc(RmUserRelationEntity::getCreateTime) // 或 updateTime
                .last("LIMIT 1")
                .one();
    }

}




