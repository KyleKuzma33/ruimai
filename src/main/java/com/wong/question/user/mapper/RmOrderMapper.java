package com.wong.question.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wong.question.user.entity.RmOrderEntity;
import com.wong.question.user.entity.dto.OrderUserDTO;
import com.wong.question.user.provider.RmOrderSqlProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Map;

@Mapper
public interface RmOrderMapper extends BaseMapper<RmOrderEntity> {

    /**
     * 查询订单查看用户
     * */
    @SelectProvider(type = RmOrderSqlProvider.class, method = "selectOrdersWithUser")
    Page<OrderUserDTO> selectOrdersWithUser(Page<OrderUserDTO> page, @Param("params") Map<String, Object> params);
}




