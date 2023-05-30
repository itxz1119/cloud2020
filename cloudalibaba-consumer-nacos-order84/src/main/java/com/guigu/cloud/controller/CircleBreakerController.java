package com.guigu.cloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.guigu.cloud.service.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class CircleBreakerController {
    public static final String SERVICE_URL = "http://nacos-payment-provider";

    @Resource
    private RestTemplate restTemplate;

    @RequestMapping("/consumer/fallback/{id}")
    //@SentinelResource(value = "fallback", fallback = "fallbackHandler")//只负责业务异常,即运行时异常
    //@SentinelResource(value = "fallback", blockHandler = "blockHandler") //只负责sentinel配置异常
    @SentinelResource(value = "fallback", fallback = "fallbackHandler", blockHandler = "blockHandler")  //一起配置
    public CommonResult fallback(@PathVariable("id") Long id) {
        CommonResult result = restTemplate.getForObject(SERVICE_URL + "/paymentSQL/" + id, CommonResult.class, id);
        if (id == 4) {
            throw new IllegalArgumentException ("IllegalArgumentException,非法参数异常....");
        }else if (result.getData() == null) {
            throw new NullPointerException ("NullPointerException,该ID没有对应记录,空指针异常");
        }
        return result;
    }
    //fallback 管运行时异常
    public CommonResult fallbackHandler(@PathVariable Long id, Throwable e){
        Payment payment = new Payment(id, null);
        return new CommonResult(500,"兜底异常fallback" + e.getMessage(), payment);
    }
    //blockHandler sentinel配置异常
    public CommonResult blockHandler(@PathVariable Long id, BlockException e){
        Payment payment = new Payment(id, null);
        return new CommonResult(500,"兜底异常blockHandler" + e.getMessage(), payment);
    }

    /*
    * openfeign部分
    * */
    @Resource
    private PaymentService paymentService;

    @GetMapping("/consumer/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable Long id){
        if (id==4){
            throw new RuntimeException("没有该id---");
        }
        return paymentService.paymentSQL(id);
    }

}
