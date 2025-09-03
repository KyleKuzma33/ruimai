package com.wong.question.user.controller;

import com.alibaba.fastjson2.JSONObject;
import com.wong.question.user.entity.RmUserEntity;
import com.wong.question.user.service.RmUserService;
import com.wong.question.utils.DESUtil;
import com.wong.question.utils.R;
import com.wong.question.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/rm/wechat")
@Api(tags = "瑞麦微信接口")
public class WeChatController {
    @Value("${wechat.mini.app-id}")
    private String appId;
    @Value("${wechat.mini.secret}")
    private String appSecret;
    @Autowired
    private RmUserService rmUserService;
    @Resource
    private RedisUtil redisUtil;
    /**
     * 小程序登录
     */
    @PostMapping("/login")
    @ApiOperation("用户登录接口")
    public R login(@RequestParam String code,
                   @RequestParam(required = false) String userName,
                   @RequestParam(required = false) String userPhone) {
        // 1. 调用微信接口获取 openid
        String url = "https://api.weixin.qq.com/sns/jscode2session"
                + "?appid=" + appId
                + "&secret=" + appSecret
                + "&js_code=" + code
                + "&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String res = restTemplate.getForObject(url, String.class);
        JSONObject json = JSONObject.parseObject(res);
        String openid = json.getString("openid");
        if (openid == null) {
            throw new RuntimeException("微信登录失败: " + res);
        }
        // 2. 登录或注册用户
        RmUserEntity user = rmUserService.loginOrRegister(openid, userName, userPhone);

        // 3. 生成 token（可以使用 UUID 或加密用户id+时间）
        String token;
        try {
            token = DESUtil.encode(user.getUserId() + "_" + System.currentTimeMillis());
        } catch (Exception e) {
            throw new RuntimeException("生成 token 失败", e);
        }
        //缓存用户登录信息
        Map<String, String> redisMap = new HashMap<>();
        String now = System.currentTimeMillis() + "";
        redisMap.put("now", now);
        redisMap.put("userName", user.getUserName());
        redisMap.put("userId", user.getUserId()+"");
        // 4. 保存 token 到 Redis，设置过期时间，比如 7 天
        redisUtil.set("login/" + token, JSONObject.toJSONString(redisMap), 1 * 24 * 3600);
        // 5. 返回 token 和用户信息
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", user);
        return R.success("登录成功",result);
    }

}
