package com.example.llcgs.android_kotlin.material.mediaplayer.provider

import android.support.v4.media.MediaMetadataCompat
import android.text.TextUtils

/**
 * com.example.llcgs.android_kotlin.material.mediaplayer.provider.MutableMediaMetadata
 * @author liulongchao
 * @since 2018/1/19
 */
class MutableMediaMetadata(var metadata: MediaMetadataCompat, var trackId: String) {

    override fun equals(other: Any?): Boolean {
        if (this === other){
            return true
        }
        if (other == null || other.javaClass != MutableMediaMetadata::class.java) {
            return false
        }
        val that = other as MutableMediaMetadata
        return TextUtils.equals(trackId, that.trackId)
    }
}