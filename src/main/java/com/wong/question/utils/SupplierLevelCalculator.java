package com.wong.question.utils;

import com.wong.question.user.entity.RmXtConfigEntity;
import org.springframework.stereotype.Component;

@Component
//获取系统配置的服务商等级工具
public class SupplierLevelCalculator {
    /**
     * @param inviteCount  被关联的数量
     * @param config 数据库系统配置的实体类
     * */
    public int calculateLevel(int inviteCount, RmXtConfigEntity config) {
        if (inviteCount >= config.getSeniorSenior()) {
            return 3; // 高级
        } else if (inviteCount >= config.getIntermediateService()) {
            return 2; // 中级
        } else if (inviteCount >= config.getPrimaryService()) {
            return 1; // 初级
        }
        return 0;
    }
}
