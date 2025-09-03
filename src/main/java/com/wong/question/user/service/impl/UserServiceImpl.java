package com.wong.question.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wong.question.user.entity.UserEntity;
import com.wong.question.user.mapper.UserMapper;
import com.wong.question.user.service.UserService;
import com.wong.question.utils.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author wong
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-12-05 15:44:18
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity>
    implements UserService {

    @Override
    public boolean exists(String name) {
        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserEntity::getName, name);
        return baseMapper.exists(queryWrapper);
    }

    @Override
    public UserEntity getOne(String name) {
        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserEntity::getName, name);
        return this.getOne(queryWrapper);
    }

    @Override
    public boolean updateLive(String userId, boolean live) {
        LambdaUpdateWrapper<UserEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(UserEntity::getLive, live);
        updateWrapper.eq(UserEntity::getId, userId);
        return this.update(updateWrapper);
    }

    @Override
    public R addOrUpdateUser(UserEntity userEntity) {
        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserEntity::getName, userEntity.getName());
        if (baseMapper.exists(queryWrapper)) {
            return R.error("新增失败，用户名已存在");
        }
        if (saveOrUpdate(userEntity)) {
            return R.success("新增成功");
        }
        return R.error("新增失败");
    }

    @Override
    public R delUser(Integer id) {
        return baseMapper.deleteById(id) > 0 ? R.success("删除成功") : R.error("删除失败");
    }

    @Override
    public Page<UserEntity> getUserList(Integer current, Integer size, String keyword) {
        Page<UserEntity> page = new Page<>(current, size);
        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(keyword)) {
            queryWrapper.like(UserEntity::getName, keyword);
        }
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public List<UserEntity> getList() {
        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(UserEntity::getName, "admin");
        return baseMapper.selectList(queryWrapper);
    }
}




