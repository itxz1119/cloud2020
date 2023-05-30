package com.atguigu.springcloud.MyHandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommonResult;

public class CustomerBlockHandler {

    public static CommonResult customerBlockHandler01(BlockException e){
        return new CommonResult<>(500,"失败之后,自定义的兜底方法01");
    }

    public static CommonResult customerBlockHandler02(BlockException e){
        return new CommonResult<>(500,"失败之后,自定义的兜底方法02");
    }
}
