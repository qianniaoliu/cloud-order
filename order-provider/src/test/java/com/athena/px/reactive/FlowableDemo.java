package com.athena.px.reactive;

import com.athena.px.lambda.LambdaDemo;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.Callable;

/**
 * @Description:
 * @Author: ShenLong
 * @CreateDate: 2018/6/19 11:18
 */
public class FlowableDemo {

    @Test
    @Ignore
    public void test(){
        Flowable.range(5,10)
                .map((x) -> x * x)
                .subscribeOn(Schedulers.single())
                .subscribe(LambdaDemo::print,System.out::println);
    }

    @Test
    public void test1(){
        Observable.fromCallable(LambdaDemo::returnStr)
                .subscribe(System.out::println);
    }

    @Test
    public void test2(){
        Flowable.range(0,5)
                .flatMapSingle((x)-> Single.just(x*x))
                .subscribe(System.out::println);
    }

    @Test
    public void test3(){
        Flowable.range(2,2)
                .doOnNext(System.out::println)
                .flatMap((x) -> Flowable.just(x+1))
                .subscribe(System.out::println);
    }
}
