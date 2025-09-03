package com.wong.question.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wong.question.user.entity.RmUserQualificationEntity;
import com.wong.question.user.entity.RmUserEntity;
import com.wong.question.user.entity.dto.QualificationReviewDTO;
import com.wong.question.user.service.RmUserQualificationService;
import com.wong.question.user.service.RmUserService;
import com.wong.question.utils.R;
import com.wong.question.utils.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/rm/qualification")
@Api(tags = "瑞麦店铺资质管理")
public class RmUserQualificationController {

    @Autowired
    private RmUserQualificationService service;

    @Autowired
    private RmUserService rmUserService;

    @ApiOperation("添加或修改资质")
    @PostMapping("/save")
    public R save(@RequestBody RmUserQualificationEntity entity) {
        String uid = UserContext.getUserId();
        RmUserEntity userEntity = rmUserService.getOne(
                new LambdaQueryWrapper<RmUserEntity>().eq(RmUserEntity::getUserId, uid)
        );
        if (userEntity == null) {
            return R.error("当前用户不存在，请先注册");
        }

        LambdaQueryWrapper<RmUserQualificationEntity> queryWrapper = new LambdaQueryWrapper<>();
        //判断订单id是否重复 一条订单一个合同和一条资质
        queryWrapper.eq(RmUserQualificationEntity::getOrderId, entity.getOrderId());
        RmUserQualificationEntity existing = service.getOne(queryWrapper);
        entity.setUserId(uid);
        //关联订单id
        entity.setOrderId(entity.getOrderId());
        entity.setUserName(userEntity.getUserName());
        entity.setUpdateTime(new Date());
        entity.setStatus(2);
        if (existing != null) {
            entity.setId(existing.getId());
            service.updateById(entity);
            return R.success("修改资质成功");
        } else {
            entity.setCreateTime(new Date());
            service.save(entity);
            return R.success("添加资质成功");
        }
    }

    @ApiOperation("查询指定用户资质")
    @GetMapping("/get")
    public R getByUserId(@RequestParam(required = true) Integer orderId) {
//        if(userId == null) {
//            // 默认获取当前登录用户
//            userId = UserContext.getUserId();
//        }
        LambdaQueryWrapper<RmUserQualificationEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RmUserQualificationEntity::getOrderId, orderId);
        RmUserQualificationEntity entity = service.getOne(queryWrapper);
        if(entity != null) {
            return R.success(entity);
        } else {
            return R.success("未查询到该用户资质");
        }
    }

    @ApiOperation("分页查询资质列表")
    @GetMapping("/list")
    public R list(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) Integer status
    ) {
        Page<RmUserQualificationEntity> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<RmUserQualificationEntity> queryWrapper = new LambdaQueryWrapper<>();
        if(userName != null && !userName.isEmpty()) {
            queryWrapper.like(RmUserQualificationEntity::getUserName, userName);
        }
        if(status != null) {
            queryWrapper.eq(RmUserQualificationEntity::getStatus, status);
        }
        IPage<RmUserQualificationEntity> pageData = service.page(page, queryWrapper);
        return R.success(pageData);
    }

    @ApiOperation("审核资质")
    @PostMapping("/reviewer")
    public R reviewer(@RequestBody QualificationReviewDTO qualificationReviewDTO) {
        String uid = UserContext.getUserId();
        Integer id = qualificationReviewDTO.getId();
        // 查询用户资质
        RmUserQualificationEntity rmUserQualification = service.getById(id);
        if (rmUserQualification == null) {
            return R.error("用户暂无该用户资质");
        }
        // 设置审核信息
        rmUserQualification.setStatus(qualificationReviewDTO.getStatus());
        rmUserQualification.setReviewRemark(qualificationReviewDTO.getReviewRemark());
        rmUserQualification.setReviewerId(uid);
        rmUserQualification.setReviewTime(new Date());
        // 更新数据库
        boolean updated = service.updateById(rmUserQualification);
        if (!updated) {
            return R.error("审核失败，请稍后重试");
        }
        return R.success("审核成功");
    }


}
