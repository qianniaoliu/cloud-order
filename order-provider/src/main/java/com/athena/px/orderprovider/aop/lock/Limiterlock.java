package com.athena.px.orderprovider.aop.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Description:
 * @Author: ShenLong
 * @CreateDate: 2018/6/21 16:13
 */
public class Limiterlock implements Lock {

    private Sync sync;

    class Sync extends AbstractQueuedSynchronizer{

        public Sync(int state){
            setState(state);
        }

        final void lock(){
            acquireShared(1);
        }

        @Override
        protected int tryAcquireShared(int arg) {
            for(;;){
                int s = getState();
                int u = s - arg;
                if(u < 0){
                    return u;
                }
                if(u > 0 && compareAndSetState(s,u)){
                    return u;
                }
            }
        }

        protected final boolean tryAcquireSharedLock(int arg){
            int s = getState();
            int u = s - arg;
            if(u > 0 && compareAndSetState(s,u)){
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            int s = getState();
            int u = getState() + arg;
            if(compareAndSetState(s,u)){
                return true;
            }
            return false;
        }

        final ConditionObject newCondition() {
            return new ConditionObject();
        }
    }

    public Limiterlock(int state) {
        this.sync = new Sync(state);
    }

    @Override
    public void lock() {
        sync.lock();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireSharedInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquireSharedLock(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireSharedNanos(1,unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
