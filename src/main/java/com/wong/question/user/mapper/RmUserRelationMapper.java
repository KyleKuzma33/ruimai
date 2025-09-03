package com.wong.question.user.mapper;

import com.wong.question.user.entity.RmUserRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 86184
* @description 针对表【rm_user_relation】的数据库操作Mapper
* @createDate 2025-09-01 09:10:18
* @Entity com.wong.question.user.entity.RmUserRelationEntity
*/
public interface RmUserRelationMapper extends BaseMapper<RmUserRelationEntity> {
    /**
     * 批量更新一级关系等级
     */
    int batchUpdateLevel1();

    /**
     * 批量更新二级关系等级
     */
    int batchUpdateLevel2();
}




