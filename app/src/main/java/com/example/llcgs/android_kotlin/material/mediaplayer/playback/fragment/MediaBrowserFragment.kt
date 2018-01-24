package com.example.llcgs.android_kotlin.material.mediaplayer.playback.fragment

import android.app.Fragment
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import com.example.llcgs.android_kotlin.material.mediaplayer.base.MediaBrowserProvider

/**
 * com.example.llcgs.android_kotlin.material.mediaplayer.playback.fragment.MediaBrowserFragment
 * @author liulongchao
 * @since 2018/1/24
 */
class MediaBrowserFragment: Fragment() {


    fun setMediaId(mediaId: String) {
        val args = Bundle(1)
        args.putString(MediaBrowserFragment.ARG_MEDIA_ID, mediaId)
        arguments = args
    }

    fun getMediaId(): String? {
        val args = arguments
        return args?.getString(ARG_MEDIA_ID)
    }

    interface MediaFragmentListener : MediaBrowserProvider {
        fun onMediaItemSelected(item: MediaBrowserCompat.MediaItem)
        fun setToolbarTitle(title: CharSequence?)
    }

    companion object {
        const val ARG_MEDIA_ID = "media_id"
    }
}