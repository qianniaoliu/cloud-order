package com.athena.px.hystrix;

import com.netflix.hystrix.HystrixCommandGroupKey;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:
 * @Author: ShenLong
 * @CreateDate: 2018/6/19 13:44
 */
public class HystrixDemo1 {

    Logger logger = LoggerFactory.getLogger(HystrixDemo1.class);

    @Test
    public void test1() throws Exception {
        new MyHystrixCommand().execute();
    }

    public String fail1(){
        logger.info("fail");
        return "fail";
    }
    public String fail1(Throwable e){
        logger.info("fail" + e.getMessage());
        return "fail";
    }

    class MyHystrixCommand extends com.netflix.hystrix.HystrixCommand<String>{
        protected MyHystrixCommand() {
            super(HystrixCommandGroupKey.Factory.asKey("test1"),1000);
        }

        @Override
        protected String run() throws Exception {
            Thread.sleep(2000);
            logger.info("success");
            return "success";
        }

        @Override
        protected String getFallback() {
            return HystrixDemo1.this.fail1();
        }
    }
}
