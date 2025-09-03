package com.wong.question.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security.aes")
@Data
public class AESConfig {
    private String secretKey;  // 会自动绑定到配置文件的 secret-key
}
