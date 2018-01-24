package com.example.llcgs.android_kotlin.material.mediaplayer.provider

import android.support.v4.media.MediaMetadataCompat

/**
 * com.example.llcgs.android_kotlin.material.mediaplayer.provider.MusicProviderSource
 * @author liulongchao
 * @since 2018/1/19
 */
interface MusicProviderSource {
    operator fun iterator(): Iterator<MediaMetadataCompat>
    companion object {
        const val CUSTOM_METADATA_TRACK_SOURCE = "__SOURCE__"
    }
}