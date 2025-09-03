package com.wong.question.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wong.question.user.entity.RmShopEntity;
import com.wong.question.user.service.RmShopService;
import com.wong.question.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "瑞麦店铺管理")
@RestController
@RequestMapping("/shop")
public class RmShopController {

    @Autowired
    private RmShopService rmShopService;

    /**
     * 分页查询
     */
    @ApiOperation("分页查询店铺列表")
    @PostMapping("/list")
    public R list(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String shopName,
            @RequestParam(required = false) Integer shopStatus
    ) {
        QueryWrapper<RmShopEntity> wrapper = new QueryWrapper<>();
        if (shopName != null && !shopName.isEmpty()) {
            wrapper.like("shop_name", shopName);
        }
        if (shopStatus != null) {
            wrapper.eq("shop_status", shopStatus);
        }
        wrapper.orderByDesc("creation_time");

        Page<RmShopEntity> page = new Page<>(pageNo, pageSize);
        Page<RmShopEntity> result = rmShopService.page(page, wrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("records", result.getRecords());
        map.put("total", result.getTotal());
        map.put("pageNo", result.getCurrent());
        map.put("pageSize", result.getSize());

        return R.success(map);
    }


    /**
     * 查询详情
     */
    @ApiOperation("根据ID查询店铺详情")
    @GetMapping("/{id}")
    public R getById(@PathVariable Long id) {
        RmShopEntity shop = rmShopService.getById(id);
        return R.success(shop);
    }

    /**
     * 新增
     */
    @ApiOperation("新增店铺")
    @PostMapping("/add")
    public R add(@RequestBody RmShopEntity shop) {
        boolean result = rmShopService.save(shop);
        return result ? R.success("新增成功") : R.error("新增失败");
    }

    /**
     * 修改
     */
    @ApiOperation("修改店铺")
    @PutMapping("/update")
    public R update(@RequestBody RmShopEntity shop) {
        boolean result = rmShopService.updateById(shop);
        return result ? R.success("修改成功") : R.error("修改失败");
    }

    /**
     * 删除
     */
    @ApiOperation("删除店铺")
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable Long id) {
        boolean result = rmShopService.removeById(id);
        return result ? R.success("删除成功") : R.error("删除失败");
    }

    /**
     * 批量删除
     */
    @ApiOperation("批量删除店铺")
    @DeleteMapping("/deleteBatch")
    public R deleteBatch(@RequestBody List<Long> ids) {
        boolean result = rmShopService.removeByIds(ids);
        return result ? R.success("批量删除成功") : R.error("批量删除失败");
    }
}
