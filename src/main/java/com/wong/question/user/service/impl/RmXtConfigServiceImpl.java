package com.wong.question.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wong.question.user.entity.RmUserEntity;
import com.wong.question.user.entity.RmXtConfigEntity;
import com.wong.question.user.mapper.RmUserRelationMapper;
import com.wong.question.user.service.RmUserService;
import com.wong.question.user.service.RmXtConfigService;
import com.wong.question.user.mapper.RmXtConfigMapper;
import com.wong.question.utils.SupplierLevelCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author 86184
* @description 针对表【rm_xt_config】的数据库操作Service实现
* @createDate 2025-08-31 18:29:49
*/
@Service
public class RmXtConfigServiceImpl extends ServiceImpl<RmXtConfigMapper, RmXtConfigEntity>
    implements RmXtConfigService{
    @Autowired
    private RmUserService userService;

    @Autowired
    private RmUserRelationMapper relationMapper;

    @Autowired
    private SupplierLevelCalculator calculator;
    @Override
    public boolean saveOrUpdateConfig(RmXtConfigEntity config) {
        // 如果前端传了 id，自动调用 update，否则调用 insert
        return this.saveOrUpdate(config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)//开启事务 防止非运行时异常导致事务不回滚。
    public void refreshAllSupplierLevels() {
        relationMapper.batchUpdateLevel1();
        relationMapper.batchUpdateLevel2();
    }
}




