package com.wong.question.user.mapper;

import com.wong.question.user.entity.RmUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 86184
* @description 针对表【rm_user(用户表)】的数据库操作Mapper
* @createDate 2025-08-26 14:05:52
* @Entity com.wong.question.user.entity.RmUserEntity
*/
public interface RmUserMapper extends BaseMapper<RmUserEntity> {
    @Select("SELECT * FROM rm_user WHERE invite_user_id = #{userId}")
    List<RmUserEntity> selectByInviteUserId(String userId);

}




