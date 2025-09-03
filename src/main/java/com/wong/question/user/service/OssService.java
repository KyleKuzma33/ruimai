package com.wong.question.user.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.wong.question.config.OssConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssService {

    private final OssConfig ossConfig;

    public OssService(OssConfig ossConfig) {
        this.ossConfig = ossConfig;
    }

    public String uploadFile(MultipartFile file) {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        try {
            OSS ossClient = new OSSClientBuilder().build(
                    ossConfig.getEndpoint(),
                    ossConfig.getAccessKeyId(),
                    ossConfig.getAccessKeySecret()
            );
            InputStream inputStream = file.getInputStream();
            ossClient.putObject(ossConfig.getBucketName(), fileName, inputStream);
            ossClient.shutdown();

            // 拼接可访问URL
            return "https://" + ossConfig.getBucketName() + "." + ossConfig.getEndpoint() + "/" + fileName;
        } catch (Exception e) {
            throw new RuntimeException("上传失败：" + e.getMessage());
        }
    }
}
