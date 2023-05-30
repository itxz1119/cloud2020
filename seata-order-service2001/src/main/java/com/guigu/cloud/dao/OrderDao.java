package com.guigu.cloud.dao;

import com.guigu.cloud.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderDao {

    /**
     * 创建订单
     */
    int create(Order order);

    /**
     * 修改订单金额
     */
    int update(@Param("userId") Long userId, @Param("status") Integer status);
}
