package com.wong.question.user.controller;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wong.question.user.entity.RmUserEntity;
import com.wong.question.user.entity.RmUserRelationEntity;
import com.wong.question.user.entity.dto.InviteTreeDTO;
import com.wong.question.user.service.RmUserRelationService;
import com.wong.question.user.service.RmUserService;
import com.wong.question.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "瑞麦用户")
@RestController
@RequestMapping("/rm/user")
public class RmUserController {
    @Resource
    private RmUserService rmUserService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private AESUtil aesUtil;
    @Autowired
    private RmUserRelationService rmUserRelationService;

    @ApiOperation("H5登录")
    @PostMapping("/login")
    public R login(@RequestBody RmUserEntity user) {
        String phone = user.getUserPhone();
        String password = user.getUserPwd();
        if (phone == null || password == null) {
            return R.error("手机号或密码不能为空");
        }
        RmUserEntity dbUser = rmUserService.login(phone, password);
        // 生成 token
        String token;
        try {
            token = DESUtil.encode(dbUser.getUserId() + "_" + System.currentTimeMillis());
        } catch (Exception e) {
            throw new RuntimeException("生成 token 失败", e);
        }
        // 缓存用户信息
        Map<String, Object> redisMap = new HashMap<>();
        redisMap.put("now", System.currentTimeMillis() + "");
        redisMap.put("userName", dbUser.getUserName());
        redisMap.put("userId", dbUser.getUserId() + "");
        redisUtil.set("login/" + token, JSONObject.toJSONString(redisMap), 1 * 24 * 3600);
        Map<String, Object> result = new HashMap<>();
        //查询用户服务商等级
        RmUserRelationEntity rmUserRelationEntity=rmUserRelationService.getLatestByParentId(dbUser.getUserId());
        if(rmUserRelationEntity!=null){
            result.put("supplier_level", rmUserRelationEntity.getSupplierLevel());
        }else {
            result.put("supplier_level", 0);
        }
        result.put("token", token);
        result.put("userInfo", dbUser);
        return R.success(result);
    }

    @PostMapping("/register")
    public R register(@RequestBody Map<String,String> data) throws Exception {
        String phone = data.get("userPhone");
        String inviteUserId = data.get("inviteUserId");
        String password = data.get("password");
        // 校验手机号
        if(phone == null || !phone.matches("^1[3-9]\\d{9}$")) {
            return R.error("手机号格式不正确");
        }
        //判断是否注册过
        if(rmUserService.getByPhone(phone)!=null){
            return R.error("该手机号已注册");
        }
        String i_UserId = "";
        if(inviteUserId != null && !"".equals(inviteUserId)){
            i_UserId = AESUtil.decrypt(inviteUserId);
            //查推荐人被关联多少人，查配置表满足哪一个等级代理商 给他代理商等级
        }
        RmUserEntity user = rmUserService.register(phone, password, i_UserId);
        //判断用户手机号是否注册过
        boolean isNew=user.getUserPhone().equals(phone);
        return R.success("注册成功");
    }


    @ApiOperation("修改用户信息")
    @PostMapping("/modify")
    public R modify(@RequestBody @Valid RmUserEntity rmUserEntity) {
        RmUserEntity updated = rmUserService.modify(rmUserEntity);
        return R.success("修改成功",updated);
    }

    @ApiOperation("删除用户")
    @PostMapping("/delete")
    public R delete(@RequestBody Long id) {
        boolean success = rmUserService.removeUser(id);
        return success ? R.success("删除成功") : R.error("删除失败");
    }

    @ApiOperation("分页查询用户列表")
    @PostMapping("/list")
    public R pageUsers(@RequestBody UserPageRequest request) {
        Page<RmUserEntity> page = new Page<>(request.getPageNum(), request.getPageSize());
        Page<RmUserEntity> result = rmUserService.pageUsers(page, request.getUserName(), request.getUserPhone(), request.getUserStatus());
        return R.success(result);
    }

    @ApiOperation("获取当前用户信息")
    @GetMapping("/mini/me")
    public R getCurrentUser(@RequestHeader("Authorization") String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return R.error("未传递 token");
        }
        String token = authorization.substring(7); // 去掉 "Bearer " 前缀

        // 从 redis 获取
        String redisKey = "login/" + token;
        String redisValue = (String) redisUtil.get(redisKey);
        if (redisValue == null) {
            return R.error("token 无效或已过期");
        }

        JSONObject obj = JSONObject.parseObject(redisValue);
        String userId = obj.getString("userId");

        // 查询数据库，保证用户是最新数据
        // 根据 userId 字段查询用户
        RmUserEntity user = rmUserService.getOne(
                new LambdaQueryWrapper<RmUserEntity>().eq(RmUserEntity::getUserId, userId)
        );
        if (user == null) {
            return R.error("用户不存在");
        }

        try {
            // AES 加密 userId
            String encryptedUserId = AESUtil.encrypt(userId);

            // 拼接邀请链接
            String inviteUrl = "http://192.168.110.20:5173/pages/login/index?U=" + encryptedUserId;

            // 生成二维码 (用 ZXing 生成 base64 图片)
            String qrCodeBase64 = QrCodeUtil.generateBase64(inviteUrl);

            // 封装返回
            Map<String, Object> result = new HashMap<>();
//            result.put("user", user);
            result.put("inviteUrl", inviteUrl);
            result.put("qrCode", qrCodeBase64);

            return R.success(result);
        } catch (Exception e) {
            return R.error("生成邀请链接失败: " + e.getMessage());
        }
    }

    @GetMapping("/inviteTree")
    public R getInviteTree() {
        String uid=UserContext.getUserId();
        InviteTreeDTO dto = rmUserService.getInviteTreeWithCount(uid);
        return R.success(dto);
    }


    @Data
    public static class UserPageRequest {
        @ApiModelProperty(value = "页码") private int pageNum = 1;
        @ApiModelProperty(value = "每页数量") private int pageSize = 10;
        @ApiModelProperty(value = "用户姓名")  private String userName;
        @ApiModelProperty(value = "用户电话")  private String userPhone;
        @ApiModelProperty(value = "用户状态，1正常，0禁用")  private Integer userStatus;
    }

}
