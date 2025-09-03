package com.wong.question.user.provider;

import org.apache.ibatis.jdbc.SQL;
import java.util.Map;

public class RmOrderSqlProvider {

    public String selectOrdersWithUser(Map<String, Object> paramsMap) {
        Map<String, Object> params = (Map<String, Object>) paramsMap.get("params");

        return new SQL(){{
            SELECT("o.id AS orderId, o.user_id AS userId, u.user_name AS userName, u.user_phone AS userPhone, " +
                    "o.status, o.commodity_id AS commodityId, o.commodity_number AS commodityNumber, o.amount, o.creation_time AS creationTime");
            FROM("rm_order o");
            LEFT_OUTER_JOIN("rm_user u ON o.user_id = u.user_id");

            if(params.get("userId") != null){
                WHERE("o.user_id = #{params.userId}");
            }
            if(params.get("status") != null){
                WHERE("o.status = #{params.status}");
            }
            if(params.get("startTime") != null){
                WHERE("o.creation_time >= #{params.startTime}");
            }
            if(params.get("endTime") != null){
                WHERE("o.creation_time <= #{params.endTime}");
            }

            ORDER_BY("o.creation_time DESC");
        }}.toString();
    }
}
