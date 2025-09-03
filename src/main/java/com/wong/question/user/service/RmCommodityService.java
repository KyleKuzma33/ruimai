package com.wong.question.user.service;

import com.wong.question.user.entity.RmCommodityEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 86184
* @description 针对表【rm_commodity(商品表)】的数据库操作Service
* @createDate 2025-08-22 14:16:17
*/
public interface RmCommodityService extends IService<RmCommodityEntity> {
    /**
     * 查询最新的一条数据(移动端）
     * */
    RmCommodityEntity getOneCommodity();
}
