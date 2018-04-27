package com.example.llcgs.android_kotlin.base.network

/**
 * OnUploadProgress

 * @author liulongchao
 * *
 * @since 2017/1/10
 */

interface OnUploadProgress {
    fun progress(msg: String, progress: Long, contentLength: Long)
}
