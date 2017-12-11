package com.example.llcgs.android_kotlin.base.rx.exception

/**
 * com.example.llcgs.android_kotlin.base.rx.exception.ObtainException
 * @author liulongchao
 * @since 2017/12/11
 */
class ObtainException(override var message:String = "", var code:String = "500", var tag:String = "") : Exception()