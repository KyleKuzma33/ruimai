package com.wong.question.user.controller;


import com.wong.question.user.entity.RmUserBenefitSummaryEntity;
import com.wong.question.user.service.RmUserBenefitSummaryService;
import com.wong.question.utils.R;
import com.wong.question.utils.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api(tags = "瑞麦用户收益管理")
@RestController
@RequestMapping("/rm/benefit")
public class RmUserBenefitSummaryController {
    @Resource
    private RmUserBenefitSummaryService rmUserBenefitSummaryService;

    @ApiOperation("获取当前用户直推间推的汇总")
    @PostMapping("/mini/getBenefit")
    public R getBenefit() {
        // 获取当前用户ID（假设是 Long 类型，如果 UserContext 返回 String，要转一下）
        String userIdStr = UserContext.getUserId();
        System.out.println("userIdStr"+userIdStr);
        // 查询收益汇总
        List<RmUserBenefitSummaryEntity> rmUserBenefitSummaryEntityList = rmUserBenefitSummaryService.getByUserId(userIdStr);
        // 封装返回数据
        Map<String, Object> map = new HashMap<>();
        map.put("records", rmUserBenefitSummaryEntityList);
        return R.success("查询成功",map);
    }

}
