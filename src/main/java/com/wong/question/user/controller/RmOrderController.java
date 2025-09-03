package com.wong.question.user.controller;

import com.wong.question.user.service.RmOrderService;
import com.wong.question.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "瑞麦订单查看")
@RestController
@RequestMapping("/rm/order")
public class RmOrderController {

    private final RmOrderService rmOrderService;

    public RmOrderController(RmOrderService rmOrderService) {
        this.rmOrderService = rmOrderService;
    }
    @ApiOperation("分页查看订单")
    @GetMapping("/userOrders")
    public R getUserOrders(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Long startTime,
            @RequestParam(required = false) Long endTime,
            @RequestParam int pageNum,
            @RequestParam int pageSize
    ) {
        Date start = startTime != null ? new Date(startTime) : null;
        Date end = endTime != null ? new Date(endTime) : null;
        Map<String, Object> params = new HashMap<>();
        params.put("records",rmOrderService.getUserOrders(userId, status, start, end, pageNum, pageSize));
        return R.success(params) ;
    }
}
