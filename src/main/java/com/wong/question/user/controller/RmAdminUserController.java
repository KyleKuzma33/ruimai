package com.wong.question.user.controller;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wong.question.user.entity.RmAdminUserEntity;
import com.wong.question.user.service.RmAdminUserService;
import com.wong.question.utils.DESUtil;
import com.wong.question.utils.R;
import com.wong.question.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.Valid;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员用户管理
 */
@Api(tags = "瑞麦管理员用户管理")
@RestController
@RequestMapping("/rm/admin-user")
public class RmAdminUserController {

    @Autowired
    private RmAdminUserService adminUserService;

    @Resource
    private RedisUtil redisUtil;
    /**
     * 查询所有管理员用户
     */
    @ApiOperation(value = "获取管理员用户列表")
    @GetMapping("/list")
    public R list() {
        List<RmAdminUserEntity> list = adminUserService.list();
        return R.success(list);
    }

    /**
     * 根据ID查询管理员用户
     */
    @ApiOperation(value = "获取管理员用户详情")
    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        RmAdminUserEntity adminUser = adminUserService.getById(id);
        if (adminUser != null) {
            return R.success(adminUser);
        }
        return R.error("用户不存在");
    }

    /**
     * 新增管理员用户
     */
    @ApiOperation(value = "新增管理员用户")
    @PostMapping("/save")
    public R save(@RequestBody RmAdminUserEntity adminUser) {
        boolean save = adminUserService.save(adminUser);
        return save ? R.success("新增成功") : R.error("新增失败");
    }

    /**
     * 更新管理员用户
     */
    @ApiOperation(value = "更新管理员用户")
    @PutMapping("/update")
    public R update(@RequestBody RmAdminUserEntity adminUser) {
        boolean update = adminUserService.updateById(adminUser);
        return update ? R.success("更新成功") : R.error("更新失败");
    }

    /**
     * 删除管理员用户
     */
    @ApiOperation(value = "删除管理员用户")
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable Long id) {
        boolean remove = adminUserService.removeById(id);
        return remove ? R.success("删除成功") : R.error("删除失败");
    }

    private static final String AES_KEY = "1234567890123456";


    /**
     * AES 解密
     */
    private String aesDecrypt(String encrypted) throws Exception {
        byte[] keyBytes = AES_KEY.getBytes("UTF-8");
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        // 去掉 Base64 换行符
        byte[] decoded = Base64.getDecoder().decode(encrypted.replaceAll("\\s+", ""));
        byte[] decrypted = cipher.doFinal(decoded);
        return new String(decrypted, "UTF-8");
    }


    @ApiOperation("管理员登录")
    @PostMapping("/login")
    public R login(@RequestBody @Valid RmAdminUserEntity adminUser) throws NoSuchPaddingException, IllegalBlockSizeException, InvalidKeySpecException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        System.out.println("adminUser====>"+adminUser);
        String account = adminUser.getUserName();
        String encryptedPwd = adminUser.getUserPwd();

        // 解密前端密码
        String password;
        try {
            password = aesDecrypt(encryptedPwd);
        } catch (Exception e) {
            return R.error("密码解密失败");
        }

        // 查询用户
        RmAdminUserEntity user = adminUserService.getOne(
                new QueryWrapper<RmAdminUserEntity>().eq("user_name", account)
        );

        if (user == null || !password.equals(user.getUserPwd())) {
            return R.error("用户名或密码错误");
        }

        // 生成 token 并存 Redis
        // 生成 token 并存 Redis
//        String rawToken = account + "_" + System.currentTimeMillis();
//        String token = DESUtil.encode(rawToken); // 加密后返回给前端

        // 缓存用户登录信息
        Map<String, String> redisMap = new HashMap<>();
        String now = System.currentTimeMillis() + "";
        String key = account + "_" + now;
        redisMap.put("key", key);
        redisMap.put("now", now);
        redisMap.put("userName", user.getUserName());
        redisMap.put("userId", user.getUserId()+"");


        String token = this.descEncode(key);
        redisUtil.set("login/" + token, JSONObject.toJSONString(redisMap), 60 * 60 * 24 * 3);
        // 清空密码返回
        user.setUserPwd("");

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("userInfo", user);

        return R.success("登录成功", map);
    }

    private String descEncode(String str) {
        try {
            return DESUtil.encode(str);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException |
                 NoSuchPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

}
