package com.wong.question.user.controller;


import com.wong.question.user.entity.RmXtConfigEntity;
import com.wong.question.user.service.RmXtConfigService;
import com.wong.question.utils.R;
import com.wong.question.utils.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/api/rm/config")
@Api(tags = "系统配置管理")
public class RmXtConfigController {

    @Autowired
    private RmXtConfigService configService;

    @PostMapping("/save")
    @ApiOperation("新增或修改系统配置")
    public R saveOrUpdate(@RequestBody RmXtConfigEntity config) {
        String currentUserId = UserContext.getUserId(); // 获取当前登录用户
        if (config.getId() == null) {
            config.setCreateUserId(currentUserId);
            config.setCreateTime(new Date());
        }
        config.setUpdateUser_id(currentUserId); // 注意这里字段名要统一
        config.setUpdateTime(new Date());
        boolean success = configService.saveOrUpdateConfig(config);
        if (success) {
            // 配置修改成功后，刷新所有用户等级
            configService.refreshAllSupplierLevels();
            return R.success("操作成功");
        } else {
            return R.error("操作失败");
        }
    }


    @GetMapping("/get")
    @ApiOperation("获取系统配置")
    public R getConfig() {
        RmXtConfigEntity config = configService.getOne(null); // 获取唯一一条配置
        return R.success(config);
    }
}
