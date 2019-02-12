package com.example.llcgs.android_kotlin.thread.thread;

import java.util.concurrent.Callable;

public class CallTask implements Callable<String> {


    @Override
    public String call() throws Exception {
        return "Hello Thread";
    }

}
