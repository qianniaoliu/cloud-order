package com.athena.px.hystrix;

import com.athena.px.api.PayService;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Description:
 * @Author: ShenLong
 * @CreateDate: 2018/6/22 19:53
 */
public class PayHystrix implements PayService {
    @Override
    public Boolean queryStatus(@RequestBody Integer id) {
        return false;
    }
}
