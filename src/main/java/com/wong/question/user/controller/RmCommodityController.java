package com.wong.question.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wong.question.user.entity.RmCommodityEntity;
import com.wong.question.user.service.RmCommodityService;
import com.wong.question.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rm/commodity")
@Api(tags = "瑞麦商品")
public class RmCommodityController {

    @Autowired
    private RmCommodityService commodityService;


    //查询最新的一条数据
    @PostMapping("/mini/getOneCommodity")
    @ApiOperation("查询最新的一条数据(移动端）")
    public R getOneCommodity(){
        RmCommodityEntity commodity = commodityService.getOneCommodity();
        if(commodity==null){return R.error("查询失败");}
        Map<String,Object> map = new HashMap<>();
        map.put("records",commodity);
        return R.success("查询成功",map);
    }


    // 新增商品
    @PostMapping("/add")
    @ApiOperation("新增商品")
    public R addCommodity(@RequestBody RmCommodityEntity commodity) {
        commodityService.save(commodity);
        return R.success("添加成功");
    }

    // 修改商品
    @PostMapping("/update")
    @ApiOperation("修改商品")
    public R updateCommodity(@RequestBody RmCommodityEntity commodity) {
        commodityService.updateById(commodity);
        return R.success("修改成功");
    }

    // 删除商品
    @GetMapping("/delete")
    @ApiOperation("删除商品")
    public R deleteCommodity(@RequestParam Long id) {
        commodityService.removeById(id);
        return R.success("删除成功");
    }

    // 根据id查询
    @GetMapping("/{id}")
    @ApiOperation("根据id查询")
    public RmCommodityEntity getById(@PathVariable Long id) {
        return commodityService.getById(id);
    }

    // 查询所有商品
    @GetMapping("/list")
    @ApiOperation("查询所有商品")
    public List<RmCommodityEntity> listAll() {
        return commodityService.list();
    }

    // 分页查询商品（可带模糊搜索）
    @GetMapping("/page")
    @ApiOperation("分页查询商品（可带模糊搜索）")
    public R pageCommodity(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String name
    ) {
        Page<RmCommodityEntity> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<RmCommodityEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(name != null, RmCommodityEntity::getCommodityName, name);

        Page<RmCommodityEntity> result = commodityService.page(page, wrapper);

        // 如果要返回总条数和列表，可以这样封装
        Map<String, Object> data = new HashMap<>();
        data.put("records", result.getRecords());  // 当前页数据
        data.put("total", result.getTotal());      // 总条数
        data.put("pages", result.getPages());      // 总页数
        data.put("current", result.getCurrent());  // 当前页码
        data.put("size", result.getSize());        // 每页大小

        return R.success(data);
    }
}
