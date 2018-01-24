package com.example.llcgs.android_kotlin.material.mediaplayer.base

import android.support.v4.media.MediaBrowserCompat

/**
 * com.example.llcgs.android_kotlin.material.mediaplayer.base.MediaBrowserProvider
 * @author liulongchao
 * @since 2018/1/19
 */
interface MediaBrowserProvider {
    fun getMediaBrowser(): MediaBrowserCompat
}