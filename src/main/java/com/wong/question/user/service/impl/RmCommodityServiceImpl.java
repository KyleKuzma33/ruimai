package com.wong.question.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wong.question.user.entity.RmCommodityEntity;
import com.wong.question.user.service.RmCommodityService;
import com.wong.question.user.mapper.RmCommodityMapper;
import org.springframework.stereotype.Service;

/**
* @author 86184
* @description 针对表【rm_commodity(商品表)】的数据库操作Service实现
* @createDate 2025-08-22 14:16:17
*/
@Service
public class RmCommodityServiceImpl extends ServiceImpl<RmCommodityMapper, RmCommodityEntity>
    implements RmCommodityService{

    @Override
    public RmCommodityEntity getOneCommodity() {
        LambdaQueryWrapper<RmCommodityEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RmCommodityEntity::getCommodityStatus,1)
                .orderByDesc(RmCommodityEntity::getCreationTime).last("LIMIT 1");
        return this.getOne(wrapper);
    }
}




