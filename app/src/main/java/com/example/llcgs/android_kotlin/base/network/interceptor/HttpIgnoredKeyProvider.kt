package com.example.llcgs.android_kotlin.base.network.interceptor

/**
 * com.example.llcgs.android_kotlin.base.network.interceptor.HttpIgnoredKeyProvider
 * @author liulongchao
 * @since 2018/5/11
 */
interface HttpIgnoredKeyProvider {

    fun provideIgnoredRequestKeys(): Array<String>?

    fun provideIgnoredResponseKeys(): Array<String>?

    fun isInIgnoredRequestKeys(key: String): Boolean {
        var i = 0
        while (provideIgnoredRequestKeys() != null && i < provideIgnoredRequestKeys()!!.size) {
            val ignoredKey = provideIgnoredRequestKeys()!![i]
            if (ignoredKey.equals(key, ignoreCase = true)) {
                return true
            }
            i++
        }
        return false
    }

    fun isInIgnoredResponseKeys(key: String): Boolean {
        var i = 0
        while (provideIgnoredResponseKeys() != null && i < provideIgnoredResponseKeys()!!.size) {
            val ignoredKey = provideIgnoredResponseKeys()!![i]
            if (ignoredKey.equals(key, ignoreCase = true)) {
                return true
            }
            i++
        }
        return false
    }
}