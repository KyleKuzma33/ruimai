package com.wong.question.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wong.question.user.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* @author wong
* @description 针对表【user】的数据库操作Mapper
* @createDate 2024-12-05 15:44:18
* @Entity com.wong.line.user.entity.UserEntity
*/
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

}




