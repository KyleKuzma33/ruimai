package com.wong.question.utils;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import com.wong.question.config.AESConfig;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class AESUtil {

    private final AESConfig aesConfig;
    private static String SECRET_KEY;

    public AESUtil(AESConfig aesConfig) {
        this.aesConfig = aesConfig;
    }

    @PostConstruct
    public void init() {
        SECRET_KEY = aesConfig.getSecretKey(); // 注入配置文件的密钥
    }

    // 加密
    public static String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encrypted = cipher.doFinal(data.getBytes("UTF-8"));
        return Base64.getUrlEncoder().encodeToString(encrypted);
    }

    // 解密
    public static String decrypt(String encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decoded = Base64.getUrlDecoder().decode(encryptedData);
        byte[] original = cipher.doFinal(decoded);
        return new String(original, "UTF-8");
    }
}
