        package com.wong.question.user.service;

        import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
        import com.wong.question.user.entity.RmOrderEntity;
        import com.baomidou.mybatisplus.extension.service.IService;
        import com.wong.question.user.entity.dto.OrderUserDTO;

        import java.util.Date;

        /**
        * @author 86184
        * @description 针对表【rm_order】的数据库操作Service
        * @createDate 2025-08-28 15:56:24
        */
        public interface RmOrderService extends IService<RmOrderEntity> {
                RmOrderEntity addOrder(RmOrderEntity order);
                void refundOrder(Long orderId);
                /**
                 * 分页查询指定用户的订单，同时返回用户信息
                 * @param userId 用户ID
                 * @param pageNum 页码
                 * @param pageSize 每页数量
                 */
                Page<OrderUserDTO> getUserOrders(String userId, Integer status, Date startTime, Date endTime, int pageNum, int pageSize);
        }
