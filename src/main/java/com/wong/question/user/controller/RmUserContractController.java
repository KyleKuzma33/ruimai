package com.wong.question.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wong.question.user.entity.RmUserContractEntity;
import com.wong.question.user.service.RmUserContractService;
import com.wong.question.utils.R;
import com.wong.question.utils.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "瑞麦用户签署管理")
@RestController
@RequestMapping("/rm/contract")
public class RmUserContractController {
    @Autowired
    private RmUserContractService rmUserContractService;

    @ApiOperation("通过用户ID查询已经签署的合同")
    @GetMapping("/getUserContractByUserId")
    public R getUserContractByUserId() {
        String uid= UserContext.getUserId();
        LambdaQueryWrapper<RmUserContractEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RmUserContractEntity::getUserId, uid);
        RmUserContractEntity rmUserContractEntity = rmUserContractService.getOne(wrapper);
      return R.success("合同查询成功", rmUserContractEntity);
    };
}
