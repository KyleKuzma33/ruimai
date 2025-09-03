package com.wong.question.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wong.question.user.entity.RmUserEntity;
import com.wong.question.user.entity.RmUserRelationEntity;
import com.wong.question.user.entity.RmXtConfigEntity;
import com.wong.question.user.entity.dto.InviteTreeDTO;
import com.wong.question.user.mapper.RmUserMapper;
import com.wong.question.user.mapper.RmUserRelationMapper;
import com.wong.question.user.mapper.RmXtConfigMapper;
import com.wong.question.user.service.RmUserService;
import com.wong.question.utils.AESUtil;
import com.wong.question.utils.SupplierLevelCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.transaction.annotation.Transactional;


import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Service
public class RmUserServiceImpl extends ServiceImpl<RmUserMapper, RmUserEntity> implements RmUserService {

    @Autowired
    private RmXtConfigMapper configMapper;

    @Autowired
    private RmUserRelationMapper relationMapper;

    @Autowired
    private SupplierLevelCalculator calculator;

    @Override
    public RmUserEntity getByPhone(String userPhone) {
        return lambdaQuery().eq(RmUserEntity::getUserPhone, userPhone).one();
    }

    @Override
    public RmUserEntity login(String userPhone, String password) {
        if (StringUtils.isBlank(userPhone) || StringUtils.isBlank(password)) {
            throw new RuntimeException("手机号和密码不能为空");
        }

        RmUserEntity user = getByPhone(userPhone);
        if (user == null) {
            throw new RuntimeException("用户不存在，请注册");
        }

        // 比对加密后的密码
        String encryptPwd = encryptPassword(password);
        if (!encryptPwd.equals(user.getUserPwd())) {
            throw new RuntimeException("手机号或密码错误");
        }

        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)//开启事物 防止非运行时异常导致事务不回滚
    public RmUserEntity register(String userPhone, String password, String inviteUserId) {
        if (StringUtils.isBlank(userPhone)) {
            throw new RuntimeException("手机号不能为空");
        }
        // 已注册过，直接返回
        RmUserEntity exist = getByPhone(userPhone);
        if (exist != null) {
            return exist;
        }
        // 密码处理
        if (StringUtils.isBlank(password)) {
            password = userPhone.length() >= 6
                    ? userPhone.substring(userPhone.length() - 6)
                    : userPhone;
        }
        String encryptPwd = encryptPassword(password);
        // 新用户
        RmUserEntity newUser = new RmUserEntity();
        newUser.setUserId(String.valueOf(System.currentTimeMillis()));
        newUser.setUserPhone(userPhone);
        newUser.setInviteUserId(inviteUserId);
        newUser.setUserPwd(encryptPwd);
        newUser.setUserName("用户" + userPhone.substring(userPhone.length() - 4));
        newUser.setCreationTime(new Date());
        this.save(newUser);
        // 推荐人关系处理  StringUtils.isNotBlank(inviteUserId)判断字符串：是否 非空并且不全是空格
        if (StringUtils.isNotBlank(inviteUserId)) {
            //mybatis-plus查询 有没有user_id等于传递inviteUserId的数据
            RmUserEntity parent = this.getOne(
                    new QueryWrapper<RmUserEntity>().eq("user_id", inviteUserId)
            );
            //有用户
            if (parent != null) {
                // 读取系统配置
                RmXtConfigEntity config = configMapper.selectById(1);
                // 计算推荐人直推人数  查用户表里 有多少个invite_user_id 等于我们查到用户parent.user_id的
                int inviteCount = (int) this.count(
                        new QueryWrapper<RmUserEntity>().eq("invite_user_id", parent.getUserId())
                );
                //管理服务商等级 1 2 3 初级高中级
                int parentLevel = calculator.calculateLevel(inviteCount, config);
                // 一级关系
                RmUserRelationEntity relation1 = new RmUserRelationEntity();
                relation1.setUserId(newUser.getUserId());
                relation1.setParentId(parent.getUserId());
                relation1.setRelationLevel(1);
                relation1.setSupplierLevel(parentLevel);
                relation1.setCreateTime(new Date());
                relationMapper.insert(relation1);
                // 二级关系（推荐人的推荐人）
                if (StringUtils.isNotBlank(parent.getInviteUserId())) {
                    RmUserEntity grandParent = this.getOne(
                            new QueryWrapper<RmUserEntity>().eq("user_id", parent.getInviteUserId())
                    );
                    if (grandParent != null) {
                        int grandInviteCount = (int) this.count(
                                new QueryWrapper<RmUserEntity>().eq("invite_user_id", grandParent.getUserId())
                        );
                        int grandLevel = calculator.calculateLevel(grandInviteCount, config);
                        RmUserRelationEntity relation2 = new RmUserRelationEntity();
                        relation2.setUserId(newUser.getUserId());
                        relation2.setParentId(grandParent.getUserId());
                        relation2.setRelationLevel(2);
                        relation2.setSupplierLevel(grandLevel);
                        relation2.setCreateTime(new Date());
                        relationMapper.insert(relation2);
                    }
                }
            }
        }
        return newUser;
    }


    /**
     * 密码加密工具（示例用MD5，可替换成BCrypt）
     */
    private String encryptPassword(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public RmUserEntity loginOrRegister(String openid, String userName, String userPhone) {
        RmUserEntity user = this.getOne(new QueryWrapper<RmUserEntity>().eq("user_phone", userPhone));
        if (user == null) {
            user = new RmUserEntity();
            user.setUserOpenid(openid);
            user.setUserPhone(userPhone);
            user.setUserName(userName != null ? userName : "用户" + System.currentTimeMillis());
            this.save(user);
        } else {
            user.setUserOpenid(openid);
            this.updateById(user);
        }
        return user;
    }

    @Override
    public RmUserEntity modify(RmUserEntity rmUserEntity) {
        this.updateById(rmUserEntity);
        return this.getById(rmUserEntity.getId());
    }

    @Override
    public boolean removeUser(Long id) {
        return this.removeById(id);
    }

    @Override
    public Page<RmUserEntity> pageUsers(Page<RmUserEntity> page, String userName, String userPhone, Integer userStatus) {
        LambdaQueryWrapper<RmUserEntity> wrapper = new LambdaQueryWrapper<>();
        if (userName != null && !userName.isEmpty()) {
            wrapper.like(RmUserEntity::getUserName, userName);
        }
        if (userPhone != null && !userPhone.isEmpty()) {
            wrapper.eq(RmUserEntity::getUserPhone, userPhone);
        }
        if (userStatus != null) {
            wrapper.eq(RmUserEntity::getUserStatus, userStatus);
        }
        return this.page(page, wrapper);
    }

    @Override
    public InviteTreeDTO getInviteTreeWithCount(String userId) {
        InviteTreeDTO result = new InviteTreeDTO();
        int[] count = {0}; // 计数用
        List<RmUserEntity> tree = buildTree(userId, count);
        result.setChildren(tree);
        result.setTotalCount(count[0]);
        return result;
    }
    private List<RmUserEntity> buildTree(String userId, int[] count) {
        List<RmUserEntity> children = baseMapper.selectByInviteUserId(userId);
        if (children != null && !children.isEmpty()) {
            for (RmUserEntity child : children) {
                count[0]++;
                child.setChildren(buildTree(child.getUserId(), count));
            }
        }
        return children;
    }
}
