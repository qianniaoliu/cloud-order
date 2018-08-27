package com.athena.px.orderprovider.serviceimpl;

import com.athena.px.api.PayService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.micrometer.core.instrument.util.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: ShenLong
 * @CreateDate: 2018/6/18 15:17
 */
@RestController
@EnableBinding(Source.class)
public class OrderServiceImpl {

    private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    @Qualifier(Source.OUTPUT)
    private MessageChannel messageChannel;


    private final RestTemplate restTemplate;

    private final PayService payService;

    private final static String CONNECT_STR = "http://cloud-pay";

    @Autowired
    public OrderServiceImpl(RestTemplate restTemplate, PayService payService) {
        this.restTemplate = restTemplate;
        this.payService = payService;
    }

    @GetMapping("/get/order")
    /*@HystrixCommand(fallbackMethod = "getOrderTimeOut",
                    commandProperties = {
                        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000")
                    })*/
    public String getOrder() throws Exception {
        logger.info("OrderServiceImpl#getOrder");
        /*Boolean status = restTemplate.getForObject(CONNECT_STR + "/get/pay/status/1",Boolean.class);*/
        Boolean status = payService.queryStatus(1);
        messageChannel.send(MessageBuilder.withPayload("Hello World").build());
        return "order : " + status;
    }

    public String getOrderTimeOut(){
        logger.error("Time Out");
        return "Time Out";
    }
}
