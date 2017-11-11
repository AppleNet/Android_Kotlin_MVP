package com.example.llcgs.android_kotlin.kotlin.classandobject.extensions.model;


import com.example.llcgs.android_kotlin.kotlin.idiom.kt.StringExtensionKt;

/**
 * com.example.llcgs.android_kotlin.classandobject.extensions.model.NineModelJava
 *
 * @author liulongchao
 * @since 2017/10/16
 */


public class NineModelJava {

    private void getLastChar(){
        // TODO Java中调用扩展属性，永远都是当前的kt文件的文件名加Kt，例如StringExtension 在java被调用的时候 使用StringExtensionKt
        StringExtensionKt.setLastChar(new StringBuilder("Kotlin?"), '!');
        StringExtensionKt.getLastChar(new StringBuilder("Kotlin?"));

    }

}
