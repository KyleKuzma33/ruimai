package com.wong.question.utils;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * DES 加解密工具类
 * 使用对称加密算法 DES，对文本进行加密和解密
 *
 * 注意：
 * 1. DES 仅支持 8 字节（64 位）的密钥，多余的部分会被忽略。
 * 2. DES 已不安全，仅用于兼容或学习，请在实际项目中使用 AES。
 */
public class DESUtil {
    /**
     * 对称加密密钥
     * DES 要求密钥长度必须是 8 字节（64 位），
     * 如果超过长度，只有前 8 个字节有效。
     */
    private static final String KEY = "&T^kJ^#9e3q1ldB6PJLku*2OL7g34MmH";

    /**
     * 生成 DES 密钥对象
     * @return Key DES 对称加密密钥
     */
    private static Key getKey() throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException {
        // 取 KEY 的字节数组（只会使用前 8 个字节）
        DESKeySpec desKeySpec = new DESKeySpec(KEY.getBytes());
        // 通过工厂类生成密钥
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
        return factory.generateSecret(desKeySpec);
    }

    /**
     * DES 加密
     * @param text 待加密的字符串
     * @return 加密后的字符串（Base64 编码）
     */
    public static String encode(String text) throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        // 创建 DES/ECB 模式，使用 PKCS5 填充
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        // 初始化为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, getKey());
        // 执行加密
        byte[] result = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
        // 使用 Base64 编码为字符串返回
        return Base64.getEncoder().encodeToString(result);
    }

    /**
     * DES 解密
     * @param text 加密后的字符串（Base64 编码）
     * @return 解密后的原始字符串
     */
    public static String decode(String text) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException, InvalidKeyException {
        // Base64 解码
        byte[] result = Base64.getDecoder().decode(text);
        // 创建解密 Cipher
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        // 初始化为解密模式
        cipher.init(Cipher.DECRYPT_MODE, getKey());
        // 执行解密
        result = cipher.doFinal(result);
        // 转换为字符串
        return new String(result, StandardCharsets.UTF_8);
    }
}
