package com.wong.question.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wong.question.user.entity.RmUserContractEntity;
import com.wong.question.user.entity.UserContractEntity;
import com.wong.question.user.entity.dto.SignContractDTO;
import com.wong.question.user.service.RmUserContractService;
import com.wong.question.user.service.UserContractService;
import com.wong.question.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Api(tags = "瑞麦用户签署合同")
@RestController
@RequestMapping("/rm/contract")
public class UserContractController {

    @Autowired
    private UserContractService userContractService;

    @Autowired
    private RmUserContractService rmUserContractService;

    /**
     * 用户签署合同
     */
    @PostMapping("/mini/sign")
    @ApiOperation("合同签署")
    public R signContract(@RequestBody SignContractDTO dto) {
        RmUserContractEntity userContractEntity = rmUserContractService.addContracts(dto);

        if (userContractEntity == null) {
            return R.error("合同签署失败");
        }

        // 判断是否是已经签署过的合同
        if (userContractEntity.getSignTime().before(new Date(System.currentTimeMillis() - 1000))) {
            return R.error("您已签署过此合同");
        }

        return R.success("合同签署成功");
    }
    /**
     * 分页查询合同列表
     */
    @GetMapping("/page")
    @ApiOperation("分页查询合同列表")
    public R page(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String contractId
    ) {
        Page<RmUserContractEntity> page = new Page<>(pageNo, pageSize);

        LambdaQueryWrapper<RmUserContractEntity> wrapper = new LambdaQueryWrapper<>();
        if (userId != null && !userId.isEmpty()) {
            wrapper.eq(RmUserContractEntity::getUserId, userId);
        }
        if (contractId != null && !contractId.isEmpty()) {
            wrapper.eq(RmUserContractEntity::getContractId, contractId);
        }
        wrapper.orderByDesc(RmUserContractEntity::getSignTime);

        Page<RmUserContractEntity> result = rmUserContractService.page(page, wrapper);
        return R.success(result);
    }
    /**
     * 合同详情查询
     */
    @GetMapping("/detail/{id}")
    @ApiOperation("根据ID查询合同详情")
    public R detail(@PathVariable Long id) {
        RmUserContractEntity contract = rmUserContractService.getById(id);
        if (contract == null) {
            return R.error("合同不存在");
        }
        return R.success(contract);
    }

}
