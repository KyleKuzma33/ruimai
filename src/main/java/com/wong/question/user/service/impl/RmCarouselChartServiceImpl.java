package com.wong.question.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wong.question.user.entity.RmCarouselChartEntity;
import com.wong.question.user.service.RmCarouselChartService;
import com.wong.question.user.mapper.RmCarouselChartMapper;
import org.springframework.stereotype.Service;

/**
* @author 86184
* @description 针对表【rm_carousel_chart(轮播图表)】的数据库操作Service实现
* @createDate 2025-08-24 12:21:03
*/
@Service
public class RmCarouselChartServiceImpl extends ServiceImpl<RmCarouselChartMapper, RmCarouselChartEntity>
    implements RmCarouselChartService{

    @Override
    public RmCarouselChartEntity getByStatus() {
        LambdaQueryWrapper<RmCarouselChartEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RmCarouselChartEntity::getCarouselStatus, 1) // 状态为 1
                .orderByDesc(RmCarouselChartEntity::getCreationTime) // 创建时间倒序
                .last("LIMIT 1"); // 只取一条
        return this.getOne(wrapper);
    }

}




