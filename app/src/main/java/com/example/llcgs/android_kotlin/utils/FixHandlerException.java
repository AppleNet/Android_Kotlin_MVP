package com.example.llcgs.android_kotlin.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.AndroidRuntimeException;

public class FixHandlerException {

    public static void fixHandlerException() {

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Looper.loop();
                    } catch (AndroidRuntimeException e) {
                        // TODO

                    }
                }
            }
        });
    }

}
