package com.wong.question.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wong.question.user.entity.UserEntity;
import com.wong.question.utils.R;

import java.util.List;

/**
* @author wong
* @description 针对表【user】的数据库操作Service
* @createDate 2024-12-05 15:44:18
*/
public interface UserService extends IService<UserEntity> {

    /**
     * 检查用户是否存在
     * @param name
     * @return
     */
    boolean exists(String name);

    /**
     * 用户名查询用户信息
     * @param name
     * @return
     */
    UserEntity getOne(String name);

    boolean updateLive(String userId, boolean live);

    R addOrUpdateUser(UserEntity userEntity);

    R delUser(Integer id);

    Page<UserEntity> getUserList(Integer current, Integer size, String keyword);

    List<UserEntity> getList();
}
