package com.wong.question.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wong.question.user.entity.RmCarouselChartEntity;
import com.wong.question.user.service.RmCarouselChartService;
import com.wong.question.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 轮播图 控制器
 */
@Api(tags = "瑞麦轮播图管理")
@RestController
@RequestMapping("/rm/carousel")
public class RmCarouselChartController {

    @Autowired
    private RmCarouselChartService rmCarouselChartService;

    /**
     * 分页查询
     */

    @ApiOperation("分页查询轮播图列表")
    @GetMapping("/list")
    public R list(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Integer status
    ) {
        QueryWrapper<RmCarouselChartEntity> wrapper = new QueryWrapper<>();
        if (status != null) {
            wrapper.eq("carousel_status", status);
        }
        wrapper.orderByAsc("carousel_chart_sort");

        Page<RmCarouselChartEntity> page = rmCarouselChartService.page(new Page<>(pageNo, pageSize), wrapper);
        System.out.println(page.getRecords());


        return R.success(page.getRecords()).put("count", page.getTotal());
    }

    /**
     * 查询详情
     */
    @ApiOperation("根据ID查询轮播图详情")
    @GetMapping("/{id}")
    public R getById(@PathVariable Long id) {
        RmCarouselChartEntity entity = rmCarouselChartService.getById(id);
        if (entity == null) {
            return R.error("轮播图不存在");
        }
        return R.success(entity);
    }

    /**
     * 新增
     */
    @ApiOperation("新增轮播图")
    @PostMapping("/add")
    public R add(@RequestBody RmCarouselChartEntity carousel) {
        // 手动生成 ID（这里用时间戳）
        carousel.setCarouselChartId(String.valueOf(System.currentTimeMillis()));

        boolean result = rmCarouselChartService.save(carousel);
        return result ? R.success("新增成功", carousel) : R.error("新增失败");
    }

    /**
     * 修改
     */
    @ApiOperation("修改轮播图")
    @PostMapping("/update")
    public R update(@RequestBody RmCarouselChartEntity carousel) {
        boolean result = rmCarouselChartService.updateById(carousel);
        return result ? R.success("修改成功", carousel) : R.error("修改失败");
    }

    /**
     * 删除
     */
    @ApiOperation("删除轮播图")
    @GetMapping("/delete")
    public R delete(@RequestParam Long id) {
        System.out.println("删除轮播图"+id);
        boolean result = rmCarouselChartService.removeById(id);
        return result ? R.success("删除成功") : R.error("删除失败");
    }

    /**
     * 批量删除
     */
    @ApiOperation("批量删除轮播图")
    @DeleteMapping("/deleteBatch")
    public R deleteBatch(@RequestBody List<Long> ids) {
        boolean result = rmCarouselChartService.removeByIds(ids);
        return result ? R.success("批量删除成功") : R.error("批量删除失败");
    }


    /**
     * 查询最新的轮播图
     * 状态展示的
     * */
    @ApiOperation("查询最新的轮播图(状态展示的)")
    @PostMapping("/mini/getChart")
    public R getChart() {
        RmCarouselChartEntity chartEntity=rmCarouselChartService.getByStatus();
        Map<String, Object> map = new HashMap<>();
        map.put("records",chartEntity);
        return R.success(map);
    }
}
