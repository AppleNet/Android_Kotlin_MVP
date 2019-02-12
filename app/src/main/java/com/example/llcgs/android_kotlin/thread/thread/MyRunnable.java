package com.example.llcgs.android_kotlin.thread.thread;

import android.util.Log;

public class MyRunnable implements Runnable {

    private final Object object = 0L;

    @Override
    public void run() {
        Log.d("MainActivity", "result: Hello testWait");
        testWait();
    }


    private void testWait(){
        try {
            synchronized (object){
                Log.d("MainActivity", "currentThreadName - 1: " + Thread.currentThread().getName());
                object.wait();
                Log.d("MainActivity", "currentThreadName - 2: " + Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void testNotify(){
        Log.d("MainActivity", "currentThreadName: -1 " + Thread.currentThread().getName());
        object.notify();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("MainActivity", "currentThreadName: -2 " + Thread.currentThread().getName());
    }
}
