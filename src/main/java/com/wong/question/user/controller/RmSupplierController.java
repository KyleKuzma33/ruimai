package com.wong.question.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wong.question.user.entity.RmSupplierEntity;
import com.wong.question.user.service.RmSupplierService;
import com.wong.question.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 供应商申请管理控制器
 */
@Api(tags = "瑞麦供应商申请管理")
@RestController
@RequestMapping("/supplier")
public class RmSupplierController {

    @Autowired
    private RmSupplierService rmSupplierService;

    /**
     * 分页查询
     */
    @ApiOperation("分页查询供应商申请列表")
    @PostMapping("/list")
    public R list(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long userId
    ) {
        QueryWrapper<RmSupplierEntity> wrapper = new QueryWrapper<>();
        if (userId != null) {
            wrapper.eq("user_id", userId);
        }
        Page<RmSupplierEntity> page = rmSupplierService.page(new Page<>(pageNo, pageSize), wrapper);
        return R.success((Map<String, Object>) page.getRecords(), (int) page.getTotal());
    }

    /**
     * 查询详情
     */
    @ApiOperation("根据ID查询供应商申请详情")
    @GetMapping("/{id}")
    public R getById(@PathVariable Long id) {
        RmSupplierEntity supplier = rmSupplierService.getById(id);
        return R.success(supplier);
    }

    /**
     * 新增
     */
    @ApiOperation("新增供应商申请")
    @PostMapping("/add")
    public R add(@RequestBody RmSupplierEntity supplier) {
        boolean result = rmSupplierService.save(supplier);
        return result ? R.success("新增成功") : R.error("新增失败");
    }

    /**
     * 修改
     */
    @ApiOperation("修改供应商申请")
    @PutMapping("/update")
    public R update(@RequestBody RmSupplierEntity supplier) {
        boolean result = rmSupplierService.updateById(supplier);
        return result ? R.success("修改成功") : R.error("修改失败");
    }

    /**
     * 删除
     */
    @ApiOperation("删除供应商申请")
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable Long id) {
        boolean result = rmSupplierService.removeById(id);
        return result ? R.success("删除成功") : R.error("删除失败");
    }

    /**
     * 批量删除
     */
    @ApiOperation("批量删除供应商申请")
    @DeleteMapping("/deleteBatch")
    public R deleteBatch(@RequestBody List<Long> ids) {
        boolean result = rmSupplierService.removeByIds(ids);
        return result ? R.success("批量删除成功") : R.error("批量删除失败");
    }
}
