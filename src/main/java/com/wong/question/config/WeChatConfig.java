package com.wong.question.config;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;   // ✅ 正确
import org.springframework.context.annotation.Configuration;


/**
 * @Configuration
 * 示该类是一个 配置类（Configuration Class）。
 *
 * 相当于传统 Spring 里 applicationContext.xml 的作用。
 *
 * 在类里可以定义 @Bean 方法，Spring 会扫描并把这些方法的返回值注册到容器中。
 * */
@Configuration
@Data
public class WeChatConfig {
    @Value("${wechat.mini.app-id}")
    private String appId;         // 小程序AppID
    @Value("${wechat.mini.secret}")
    private String appSecret;     // 小程序Secret
    @Value("${wechat.redirect-uri:http://localhost:8080/callback}")
    private String redirectUri;   // 回调地址（需微信后台配置）
}
