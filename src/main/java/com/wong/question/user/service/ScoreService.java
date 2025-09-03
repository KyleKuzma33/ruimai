package com.wong.question.user.service;

import com.wong.question.user.entity.ScoreEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wong.question.utils.R;

/**
* @author wong
* @description 针对表【score】的数据库操作Service
* @createDate 2025-08-12 18:28:47
*/
public interface ScoreService extends IService<ScoreEntity> {
    R getScoreList();
}
