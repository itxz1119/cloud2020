package com.guigu.cloud.service.impl;

import com.guigu.cloud.dao.OrderDao;
import com.guigu.cloud.domain.Order;
import com.guigu.cloud.service.AccountService;
import com.guigu.cloud.service.OrderService;
import com.guigu.cloud.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;
    @Resource
    private AccountService accountService;
    @Resource
    private StorageService storageService;

    @Override
    public void create(Order order) {
        log.info("开始创建订单");
        orderDao.create(order);
        log.info("订单微服务调用库存,进行库存扣减");
        storageService.decrease(order.getProductId(),order.getCount());
        log.info("订单微服务调用库存,库存扣减end");
        log.info("订单微服务调用账户,进行账户扣减");
        accountService.decrease(order.getProductId(), order.getMoney());
        log.info("订单微服务调用库存,进行账户扣减End");
        //修改订单状态
        log.info("修改订单状态开始");
        orderDao.update(order.getUserId(), 0);
        log.info("修改订单状态End");
    }
}
