package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;


    @GetMapping({"/payment/nacos/{id}"})
    public String getPayment(@PathVariable("id") int id){
        return "nacos registry,  serverPort:" + serverPort + "    id:" + id;
    }


    /*
    * 服务熔断
    * */

    static Map<Long, Payment> map = new HashMap<>();

    static {
        map.put(1L,new Payment(1L,"1111111111111111"));
        map.put(2L,new Payment(2L,"22222222222222222"));
        map.put(3L,new Payment(3L,"3333333333333333"));
    }

    @GetMapping(value = "/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id)
    {
        Payment payment = map.get(id);
        CommonResult<Payment> result = new CommonResult(200,"from mysql,serverPort:  "+serverPort,payment);
        return result;
    }

}
