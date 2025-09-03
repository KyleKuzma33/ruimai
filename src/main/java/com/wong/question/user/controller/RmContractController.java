package com.wong.question.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wong.question.user.entity.RmContractEntity;
import com.wong.question.user.service.RmContractService;
import com.wong.question.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 合同表 控制器
 */
@RestController
@RequestMapping("/contract")
@Api(tags = "瑞麦合同")
public class RmContractController {

    @Autowired
    private RmContractService rmContractService;

    /**
     * 新增合同
     */
    @ApiOperation("新增合同")
    @PostMapping("/add")
    public R addContract(@RequestBody RmContractEntity contract) {
        // 自动生成合同编号，例如 "HT20250824001"
        String contractId = "HT" + System.currentTimeMillis();
        contract.setContractId(contractId);

        // 设置创建时间
        contract.setCreationTime(new Date());
        contract.setUpdateTime(new Date());

        rmContractService.save(contract);
        return R.success("创建成功");
    }


    /**
     * 根据ID删除合同
     */
    @GetMapping("/delete")
    @ApiOperation("根据ID删除合同")
    public R deleteContract(@RequestParam Long id) {
        rmContractService.removeById(id);
        return R.success("删除成功") ;
    }

    /**
     * 更新合同
     */
    @PostMapping("/update")
    @ApiOperation("更新合同")

    public R updateContract(@RequestBody RmContractEntity contract) {
        rmContractService.updateById(contract);
        return R.success("修改成功");
    }

    /**
     * 根据ID查询合同
     */
    @GetMapping("/get/{id}")
    @ApiOperation("根据ID查询合同")
    public R getContract(@PathVariable Long id) {
        return R.success(rmContractService.getById(id));
    }

    /**
     * 查询所有合同
     */
    @ApiOperation("分页查询合同列表")
    @GetMapping("/list")
    public R list(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String contractType,
            @RequestParam(required = false) Integer contractStatus
    ) {
        QueryWrapper<RmContractEntity> wrapper = new QueryWrapper<>();
        if (contractType != null && !contractType.isEmpty()) {
            wrapper.eq("contract_type", contractType);
        }
        if (contractStatus != null) {
            wrapper.eq("contract_status", contractStatus);
        }
        wrapper.orderByDesc("creation_time");

        Page<RmContractEntity> page = new Page<>(pageNo, pageSize);
        Page<RmContractEntity> result = rmContractService.page(page, wrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("records", result.getRecords());
        map.put("total", result.getTotal());
        map.put("pageNo", result.getCurrent());
        map.put("pageSize", result.getSize());

        return R.success(map);
    }

    /**
     * 查寻最新的一条 合同
     * */
    @ApiOperation("查寻最新的一条合同(移动端)")
    @PostMapping("/mini/queryNewContract")
    public R miniQueryNewContract() {
        RmContractEntity rmContractEntity = rmContractService.queryNewContract();
        if (rmContractEntity == null){return R.error("暂无数据");}
        Map<String, Object> map = new HashMap<>();
        map.put("record", rmContractEntity);
        return R.success("查询成功",map);
    }

}
