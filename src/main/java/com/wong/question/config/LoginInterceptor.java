package com.wong.question.config;

import com.alibaba.fastjson2.JSONObject;
import com.wong.question.utils.DESUtil;
import com.wong.question.utils.RedisUtil;
import com.wong.question.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器，用于验证用户请求是否携带有效的 token。
 * 依赖 Redis 存储 token 和 DES 加解密工具 DESUtil。
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private RedisUtil redisUtil; // 注入 Redis 工具，用于获取/更新 token 信息

    @Value("${spring.profiles.active}")
    private String active; // 当前 Spring 环境（dev、prod 等），可以用于开发环境放行


    /**
     * 处理请求前的拦截逻辑
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 处理器对象
     * @return true 表示放行，false 表示拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String url = request.getServletPath();
        // 放行 login 接口
        if (url.equals("/wechat/login")) {
            return true;
        }
        if (url.equals("/rm/admin-user/login")) {
            return true;
        }
        if (url.equals("/rm/user/login")) {
            return true;
        }
        if (url.equals("/rm/user/register")) {
            return true;
        }

        // 1. 如果是 OPTIONS 请求（跨域预检），直接放行
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        // 2. 从请求头获取 Authorization
        String auth = request.getHeader("Authorization");
        System.out.println("auth"+auth);
        // 3. 如果 Authorization 为空或不以 "Bearer " 开头，返回 401
        if (auth == null || !auth.startsWith("Bearer ")) {
            // 开发环境可放行（可选逻辑被注释掉）
            // if ("dev".equals(active)) { return true; }
            response.setStatus(401);
            return false;
        }

        // 4. 拆分 token
        String[] authArr = auth.split(" ");
        if (authArr.length != 2) {
            System.out.println("authArr"+authArr);
            response.setStatus(401);
            return false;
        }

        // 5. 从 Redis 检查 token 是否存在
        String token = authArr[1]; // 实际 token
        String data = (String) redisUtil.get("login/" + token); // Redis key: login/{token}
        if (data == null) {
            response.setStatus(401); // token 不存在或已过期
            return false;
        }

        // 6. 从 token 解密出时间戳
        String time;
        try {
            time = DESUtil.decode(token); // DES 解密 token
            time = time.substring(time.lastIndexOf("_")+1); // 截取时间部分（假设 token 格式为 xxx_时间戳）
        } catch (Exception e) {
            response.setStatus(401); // 解密失败
            return false;
        }

        // 7. 验证 token 的最后更新时间是否与 Redis 中存储一致
//        JSONObject obj = JSONObject.parse(data);
//        String now = obj.getString("now"); // Redis 中的最后更新时间
//        if (!now.equals(time)) {
//            System.out.println("token 已过期或被覆盖");
//            response.setStatus(401); // token 已过期或被覆盖
//            return false;
//        }
        JSONObject obj = JSONObject.parseObject(data);
        String userId = obj.getString("userId");

        // 存入上下文
        UserContext.setUserId(userId);

        // 8. 验证通过，允许请求继续
        return true;
    }

    /**
     * 请求完成后的逻辑处理（更新 token 时间或清除 token）
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 处理器对象
     * @param ex 异常信息
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        if ("OPTIONS".equals(request.getMethod())) {
            return;
        }

        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) {
            return;
        }

        String token = auth.split(" ")[1];
        String redisKey = "login/" + token;
        String data = (String) redisUtil.get(redisKey);

        if (data == null) {
            return;
        }

        try {
            JSONObject obj = JSONObject.parse(data);

            String url = request.getServletPath();
            if (url.contains("/sys/logout")) {
                // 删除 Redis 中 token
                redisUtil.setRemove(redisKey);
            } else {
                // 刷新时间戳
                String now = System.currentTimeMillis() + "";
                String account = obj.getString("userName");
                String newKey = account + "_" + now;

                obj.put("key", newKey);
                obj.put("now", now);

                // 重新保存，延长有效期
                redisUtil.set(redisKey, obj.toJSONString(), 60 * 60 * 24 * 3);
            }
        } catch (Exception e) {
            response.setStatus(401);
        }
    }

}
