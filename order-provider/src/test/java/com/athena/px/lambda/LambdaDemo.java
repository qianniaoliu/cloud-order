package com.athena.px.lambda;

/**
 * @Description:
 * @Author: ShenLong
 * @CreateDate: 2018/6/19 11:47
 */
public class LambdaDemo {

    public static void print(Integer x) throws Exception {
        System.out.println(x);
        throw new Exception("11");
        /*System.out.println("执行结束!");*/
    }

    public static String returnStr(){
        return "12343243242";
    }
}
