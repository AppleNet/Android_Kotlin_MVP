package com.example.llcgs.android_kotlin.net.webview.js

/**
 * com.example.llcgs.android_kotlin.net.webview.js.JsBridge
 * @author liulongchao
 * @since 2018/5/9
 */
object JsBridge {
    fun clazz(clazz: Class<*>): NativeMethodInjectHelper = NativeMethodInjectHelper.clazz(clazz)
}