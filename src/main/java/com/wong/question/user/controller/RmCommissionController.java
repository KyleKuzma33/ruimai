package com.wong.question.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wong.question.user.entity.RmCommissionEntity;
import com.wong.question.user.service.RmCommissionService;
import com.wong.question.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "瑞麦佣金管理")
@RestController
@RequestMapping("/commission")
public class RmCommissionController {

    @Autowired
    private RmCommissionService rmCommissionService;

    /**
     * 分页查询
     */
    @ApiOperation("分页查询佣金列表")
    @GetMapping("/list")
    public R list(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        Page<RmCommissionEntity> page = new Page<>(pageNo, pageSize);
        Page<RmCommissionEntity> result = rmCommissionService.page(page, new QueryWrapper<>());
        return R.success(result);
    }


    /**
     * 查询详情
     */
    @ApiOperation("根据ID查询佣金详情")
    @GetMapping("/{id}")
    public R getById(@PathVariable Long id) {
        return R.success(rmCommissionService.getById(id));
    }

    /**
     * 新增
     */
    @ApiOperation("新增佣金")
    @PostMapping("/add")
    public R add(@RequestBody RmCommissionEntity commission) {
        boolean save = rmCommissionService.save(commission);
        return save ? R.success("添加成功") : R.error("添加失败");
    }

    /**
     * 修改
     */
    @ApiOperation("修改佣金")
    @PutMapping("/update")
    public R update(@RequestBody RmCommissionEntity commission) {
        boolean update = rmCommissionService.updateById(commission);
        return update ? R.success("修改成功") : R.error("修改失败");
    }

    /**
     * 删除
     */
    @ApiOperation("删除佣金")
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable Long id) {
        boolean remove = rmCommissionService.removeById(id);
        return remove ? R.success("删除成功") : R.error("删除失败");
    }

    /**
     * 批量删除
     */
    @ApiOperation("批量删除佣金")
    @DeleteMapping("/deleteBatch")
    public R deleteBatch(@RequestBody List<Long> ids) {
        boolean remove = rmCommissionService.removeByIds(ids);
        return remove ? R.success("批量删除成功") : R.error("批量删除失败");
    }
}
