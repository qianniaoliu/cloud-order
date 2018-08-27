package com.athena.px.orderprovider.config;

import com.athena.px.hystrix.PayHystrix;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RetryRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Description:
 * @Author: ShenLong
 * @CreateDate: 2018/6/18 13:59
 */
@Configuration
public class EurekaConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public IRule randomRule(){
        return new RetryRule(new RandomRule(),3);
    }

    @Bean
    public PayHystrix payHystrix(){
        return new PayHystrix();
    }
}
