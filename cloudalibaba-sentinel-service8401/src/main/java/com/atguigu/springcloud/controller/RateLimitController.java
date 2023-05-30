package com.atguigu.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.MyHandler.CustomerBlockHandler;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimitController {

    //按照资源名 SentinelResource 中的value
    @GetMapping("/byResource")
    @SentinelResource(value = "byResource", blockHandler = "handleException")
    public CommonResult byResource(){
        return new CommonResult(200,"按资源名称限流OK", new Payment(2022L,"byResource"));
    }

    public CommonResult handleException(BlockException e){
        return new CommonResult(500,"按资源名称限流False");
    }

    //按照url
    @GetMapping("/rateLimit/byUrl")
    @SentinelResource(value = "byUrl")
    public CommonResult byUrl(){
        return new CommonResult(200,"按URL限流OK", new Payment(2022L,"byUrl"));
    }

    //自定义哪个类中的某个方法进行兜底
    @GetMapping("/customerBlockHandler")
    @SentinelResource(value = "customerBlockHandler", blockHandlerClass = CustomerBlockHandler.class,
            blockHandler = "customerBlockHandler02")
    public CommonResult customerBlockHandler(){
        return new CommonResult(200,"客户自定义", new Payment(2022L,"customerBlockHandler"));
    }
}
