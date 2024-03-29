package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {
    @Value("${server.port}")
    private String serverPort;
    @Resource
    PaymentService paymentService;

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("====插入结果:" + result);
        if (result > 0){

            return new CommonResult(200, "插入数据库成功", result);
        }else {
            return new CommonResult(444, "插入数据库失败", result);
        }
    }
    @GetMapping (value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("====查询结果:" + payment);

        if (payment != null){
            return new CommonResult(200, "查询成功,服务端口号:" + serverPort, payment);
        }else {
            return new CommonResult(444, "没有对应记录,查询id:" + id, null);
        }
    }
    @GetMapping("/payment/lb")
    public String getLb(){
        return serverPort;
    }

    @GetMapping(value = "/payment/feign/timeout")
    public String paymentTimeout(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return serverPort;
    }
}
