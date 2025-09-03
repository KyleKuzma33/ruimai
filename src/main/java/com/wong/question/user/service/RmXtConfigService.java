package com.wong.question.user.service;

import com.wong.question.user.entity.RmXtConfigEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 86184
* @description 针对表【rm_xt_config】的数据库操作Service
* @createDate 2025-08-31 18:29:49
*/
public interface RmXtConfigService extends IService<RmXtConfigEntity> {
    /**
     * 新增或修改配置
     */
    boolean saveOrUpdateConfig(RmXtConfigEntity config);

    void refreshAllSupplierLevels();
}
