package com.company.concurrency;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLock {




    public static void main(String[] args) {

        LockDemo lockDemo = new LockDemo();
        Thread thread1 = new Thread(()->{
            lockDemo.read();
        });
        Thread thread2 = new Thread(()->{
            lockDemo.read();
        });
        thread2.start();
        thread1.start();
    }


}
class LockDemo{

    private  ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    private ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();


    public  void read() {
        readLock.lock();
        System.out.println("读读读"+Thread.currentThread().getName());
        int j = 0;
        for(int i=0;i<9999999;i++){
            j++;
            try {
                System.out.println(j);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
        readLock.unlock();
    }
    public void write(){
        writeLock.lock();
        System.out.println("写写写");
        int j = 0;
        for(int i=0;i<999;i++) {
            j++;
            try {
                System.out.println(j);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        writeLock.unlock();
    }

}
