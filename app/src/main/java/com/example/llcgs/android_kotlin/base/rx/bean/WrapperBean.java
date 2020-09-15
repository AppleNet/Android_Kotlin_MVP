package com.example.llcgs.android_kotlin.base.rx.bean;

import android.support.annotation.NonNull;

/**
 * com.example.llcgs.android_kotlin.base.rx.bean.WrapperBean
 * @author liulongchao
 * @since 2017/12/11
 */
public class WrapperBean<T> implements Wrapper {
    private String message;
    private String showMsg;
    private T data;
    private String code;
    private String requestUrl;

    @NonNull
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @NonNull
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShowMsg() {
        return showMsg;
    }

    public void setShowMsg(String showMsg) {
        this.showMsg = showMsg;
    }

    @Override
    public String toString() {
        return "WrapperBean{" +
                "message='" + message + '\'' +
                ", showMsg='" + showMsg + '\'' +
                ", data=" + data +
                ", code='" + code + '\'' +
                ", requestUrl='" + requestUrl + '\'' +
                '}';
    }

    @Override
    public void setTag(@NonNull String tag) {
        this.requestUrl = tag;
    }

    @NonNull
    @Override
    public String getTag() {
        return requestUrl;
    }
}
