package com.example.llcgs.android_kotlin.kotlin.stringandregex.model;

import android.util.Log;

/**
 * ThirtyTwoModelJava
 *
 * @author liulongchao
 * @since 2017/10/18
 */


public class ThirtyTwoModelJava {

    public void getSplit(){
        String[] split = "12.345-6.A".split(".");
        String[] split1 = "12.345-6.A".split("\\.|-");
        Log.d("MainActivity", "length: "+split.length);
        Log.d("MainActivity", "length: "+split1.length);
    }

}
