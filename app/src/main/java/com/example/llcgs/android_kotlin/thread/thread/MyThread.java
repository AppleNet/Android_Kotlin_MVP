package com.example.llcgs.android_kotlin.thread.thread;

import android.util.Log;

public class MyThread extends Thread {

    @Override
    public void run() {
        super.run();
        Log.d("MainActivity", "MyThread result: Hello Thread");
    }
}
