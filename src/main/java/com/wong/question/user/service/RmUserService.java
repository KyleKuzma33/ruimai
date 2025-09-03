package com.wong.question.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wong.question.user.entity.RmUserEntity;
import com.wong.question.user.entity.dto.InviteTreeDTO;

public interface RmUserService extends IService<RmUserEntity> {

    /**
     * 根据手机号查询用户
     */
    RmUserEntity getByPhone(String userPhone);

    /**
     * 登录
     */
    RmUserEntity login(String userPhone, String password);

    /**
     * 注册
     */
    RmUserEntity register(String userPhone, String password,String inviteuserid);

    RmUserEntity loginOrRegister(String openid, String userName, String userPhone);

    /** 修改用户信息 */
    RmUserEntity modify(RmUserEntity rmUserEntity);

    /** 删除用户 */
    boolean removeUser(Long id);

    /** 分页查询用户 */
    Page<RmUserEntity> pageUsers(Page<RmUserEntity> page, String userName, String userPhone, Integer userStatus);
    /**
     * 根据用户ID获取邀请树及总人数
     * @param userId 用户ID
     * @return 包含子用户列表及总人数
     */
    InviteTreeDTO getInviteTreeWithCount(String userId);

}
