package com.example.llcgs.android_kotlin.base.network.interceptor

/**
 * com.example.llcgs.android_kotlin.base.network.interceptor.SimpleHttpIgnoredKeyProvider
 * @author liulongchao
 * @since 2018/5/16
 */
class SimpleHttpIgnoredKeyProvider: HttpIgnoredKeyProvider {

    private val IGNORED_REQUEST_KEYS = arrayOf("deviceFingerprint")

    override fun provideIgnoredResponseKeys(): Array<String>? = null

    override fun provideIgnoredRequestKeys(): Array<String>? = IGNORED_REQUEST_KEYS
}