package com.wong.question.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wong.question.user.entity.RmBenefitsEntity;
import com.wong.question.user.service.RmBenefitsService;
import com.wong.question.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "瑞麦收益管理")
@RestController
@RequestMapping("/benefits")
public class RmBenefitsController {

    @Autowired
    private RmBenefitsService rmBenefitsService;

    /**
     * 分页查询
     */
    @ApiOperation("分页查询收益列表")
    @PostMapping("/list")
    public R list(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long benefitsUserId,
            @RequestParam(required = false) Integer benefitsType
    ) {
        QueryWrapper<RmBenefitsEntity> wrapper = new QueryWrapper<RmBenefitsEntity>()
                .eq(userId != null, "user_id", userId)
                .eq(benefitsUserId != null, "benefits_user_id", benefitsUserId)
                .eq(benefitsType != null, "benefits_type", benefitsType)
                .orderByAsc("creation_time");

        Page<RmBenefitsEntity> page = rmBenefitsService.page(new Page<>(pageNo, pageSize), wrapper);

        // 建议统一返回结构，而不是强转成 Map
        Map<String, Object> result = new HashMap<>();
        result.put("records", page.getRecords());
        result.put("total", page.getTotal());

        return R.success(result);
    }


    /**
     * 查询详情
     */
    @ApiOperation("根据ID查询收益详情")
    @GetMapping("/{id}")
    public R getById(@PathVariable Long id) {
        RmBenefitsEntity benefits = rmBenefitsService.getById(id);
        return R.success(benefits);
    }






}
