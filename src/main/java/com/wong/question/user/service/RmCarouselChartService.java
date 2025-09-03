package com.wong.question.user.service;

import com.wong.question.user.entity.RmCarouselChartEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 86184
* @description 针对表【rm_carousel_chart(轮播图表)】的数据库操作Service
* @createDate 2025-08-24 12:21:03
*/
public interface RmCarouselChartService extends IService<RmCarouselChartEntity> {
        RmCarouselChartEntity getByStatus();
}
