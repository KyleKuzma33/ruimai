package com.wong.question.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wong.question.user.entity.dto.ConsumeUserInfoDTO;
import com.wong.question.user.service.RmBenefitLogService;
import com.wong.question.utils.R;
import com.wong.question.utils.UserContext;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wong.question.user.entity.dto.RmBenefitsLogDTO;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/rm/benefitLog")
@Api(tags = "收益明显")
public class RmBenefitLogController {
    @Resource
    private RmBenefitLogService rmBenefitLogService;

    @GetMapping("/page")
    public IPage<RmBenefitsLogDTO> getBenefitLogPage(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String fromUserName,
            @RequestParam(required = false) String benefitUserName,
            @RequestParam(required = false) Integer status) {

        return rmBenefitLogService.getBenefitLogPage(pageNo, pageSize, fromUserName, benefitUserName, status);
    }

    @GetMapping("/userDetail")
    public R getBenefitDetail(
            @RequestParam(value = "level", required = false) Integer level,
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize
    ) {
        System.out.println(level+","+pageNo+","+pageSize);
        String userIdStr = UserContext.getUserId();
        IPage<ConsumeUserInfoDTO> page = rmBenefitLogService.getBenefitDetailByUserId(userIdStr, level, pageNo, pageSize);
        return R.success(page);
    }
}
