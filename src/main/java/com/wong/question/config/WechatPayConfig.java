//package com.wong.question.config;
//
//import com.wechat.pay.java.core.RSAAutoCertificateConfig;
//import com.wechat.pay.java.service.payments.h5.H5Service;
//import com.wechat.pay.java.core.notification.NotificationParser;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class WechatPayConfig {
//
//    // 建议从 application.yml 读取
//    private static final String MCH_ID = "你的商户号";
//    private static final String MCH_SERIAL_NO = "你的商户API证书序列号";
//    private static final String PRIVATE_KEY_PATH = "D:/certs/apiclient_key.pem";
//    private static final String API_V3_KEY = "你的APIv3密钥";
//    private static final String APP_ID = "你的appid";
//
//
//
//    @Bean
//    public RSAAutoCertificateConfig rsaAutoCertificateConfig() {
//        return new RSAAutoCertificateConfig.Builder()
//                .merchantId(MCH_ID)
//                .merchantSerialNumber(MCH_SERIAL_NO)
//                .privateKeyFromPath(PRIVATE_KEY_PATH)
//                .apiV3Key(API_V3_KEY)
//                .build();
//    }
//
//    @Bean
//    public H5Service h5Service(RSAAutoCertificateConfig config) {
//        return new H5Service.Builder().config(config).build();
//    }
//
//    @Bean
//    public NotificationParser notificationParser(RSAAutoCertificateConfig config) {
//        return new NotificationParser(config);
//    }
//
//    @Bean
//    public String appId() {
//        return APP_ID;
//    }
//}