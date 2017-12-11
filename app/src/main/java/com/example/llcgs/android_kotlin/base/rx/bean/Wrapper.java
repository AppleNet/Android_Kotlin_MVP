package com.example.llcgs.android_kotlin.base.rx.bean;

/**
 * com.example.llcgs.android_kotlin.base.rx.bean.Wrapper
 * @author liulongchao
 * @since 2017/12/11
 */
public interface Wrapper<T> extends TagReceiver {

    String getShowMsg();

    T getData();
}

