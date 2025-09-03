package com.wong.question.user.service;

import com.wong.question.user.entity.RmUserRelationEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 86184
* @description 针对表【rm_user_relation】的数据库操作Service
* @createDate 2025-09-01 09:10:18
*/
public interface RmUserRelationService extends IService<RmUserRelationEntity> {
    /**
     * 根据用户 ParentID 查询最新关系
     */
    RmUserRelationEntity getLatestByParentId(String parent_id);
}
