package com.athena.px.api;

import com.athena.px.hystrix.PayHystrix;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author: ShenLong
 * @CreateDate: 2018/6/22 19:51
 */
@FeignClient(value = "cloud-pay",fallback = PayHystrix.class)
public interface PayService {

    @PostMapping(value = "/get/pay/status")
    Boolean queryStatus(@RequestBody Integer id);
}
