package com.example.llcgs.android_kotlin.thread.thread;

import android.util.Log;

public class WaitNotifyInterrupt {

    private static Object obj = new Object();

    public static void main(){
        // 创建线程
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("MainActivity", "----begin----");
                    synchronized (obj){
                        obj.wait();
                    }
                    Log.d("", "----edn----");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        threadA.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("MainActivity", "----begin interrupt threadA----");
        threadA.interrupt();
        Log.d("MainActivity", "----end interrupt threadA----");
    }

}
