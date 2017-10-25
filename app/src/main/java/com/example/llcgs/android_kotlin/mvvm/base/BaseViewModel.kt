package com.example.llcgs.android_kotlin.mvvm.base

import android.databinding.BaseObservable
import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

/**
 * com.example.llcgs.android_kotlin.mvvm.base.BaseViewModel
 * @author liulongchao
 * @since 2017/10/20
 */


abstract class BaseViewModel : BaseObservable() {

    @Suppress("DEPRECATED_JAVA_ANNOTATION")
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    protected annotation class Command

    @Suppress("DEPRECATED_JAVA_ANNOTATION")
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    protected annotation class BindView

}