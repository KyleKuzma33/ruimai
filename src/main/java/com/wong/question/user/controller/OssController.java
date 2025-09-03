package com.wong.question.user.controller;

import com.wong.question.user.service.OssService;
import com.wong.question.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/rm/oss")
@Api(tags = "瑞麦OSS")
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping("/upload")
    @ApiOperation("上传文件")
    public R upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return R.error("文件不能为空");
        }
        String url = ossService.uploadFile(file);
        return R.success("上传成功", url);
    }
}
