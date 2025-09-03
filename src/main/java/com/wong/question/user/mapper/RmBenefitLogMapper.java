package com.wong.question.user.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wong.question.user.entity.RmBenefitLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wong.question.user.entity.dto.ConsumeUserInfoDTO;
import com.wong.question.user.entity.dto.RmBenefitsLogDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 86184
* @description 针对表【rm_benefit_log(分销收益记录表)】的数据库操作Mapper
* @createDate 2025-08-28 16:38:38
* @Entity com.wong.question.user.entity.RmBenefitLogEntity
*/
public interface RmBenefitLogMapper extends BaseMapper<RmBenefitLogEntity> {
    IPage<RmBenefitsLogDTO> queryBenefitLogPage(Page<?> page,
                                                @Param("fromUserName") String fromUserName,
                                                @Param("benefitUserName") String benefitUserName,
                                                @Param("status") Integer status);


    @Select("<script>" +
            "SELECT " +
            "b.from_user_id AS fromUserId, " +
            "u.user_name AS fromUserName, " +
            "b.level AS level, " +
            "o.amount AS consumeAmount, " +
            "b.amount AS benefitAmount, " +
            "o.id AS orderId, " +
            "o.creation_time AS payTime " +
            "FROM rm_benefit_log b " +
            "LEFT JOIN rm_order o ON b.order_id = o.id " +
            "LEFT JOIN rm_user u ON b.from_user_id = u.user_id " +
            "WHERE b.benefit_user_id = #{benefitUserId} " +
            "AND b.status = 1 " +
            "AND o.status = 1 " +
            "<if test='level != null'> " +
            "   AND b.level = #{level} " +
            "</if>" +
            "ORDER BY o.creation_time DESC" +
            "</script>")
    IPage<ConsumeUserInfoDTO> getBenefitDetailByUserId(
            IPage<ConsumeUserInfoDTO> page,
            @Param("benefitUserId") String benefitUserId,
            @Param("level") Integer level);
}




