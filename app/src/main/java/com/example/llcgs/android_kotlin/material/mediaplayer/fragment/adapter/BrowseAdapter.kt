package com.example.llcgs.android_kotlin.material.mediaplayer.fragment.adapter

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.utils.media.MediaIDHelper

/**
 * com.example.llcgs.android_kotlin.material.mediaplayer.fragment.adapter.BrowseAdapter
 * @author liulongchao
 * @since 2018/1/25
 */
class BrowseAdapter: BaseQuickAdapter<MediaBrowserCompat.MediaItem, BaseViewHolder>(R.layout.view_media_item_ry) {

    val STATE_INVALID = -1
    val STATE_NONE = 0
    val STATE_PLAYABLE = 1
    val STATE_PAUSED = 2
    val STATE_PLAYING = 3

    override fun convert(helper: BaseViewHolder, item: MediaBrowserCompat.MediaItem) {
        helper.setText(R.id.title, item.description.title)
        helper.setText(R.id.description, item.description.subtitle)
        val drawable = getDrawableByState(mContext, getMediaItemState(mContext, item))
        helper.setImageDrawable(R.id.play_eq, drawable)
    }

    private fun getMediaItemState(context: Context, item: MediaBrowserCompat.MediaItem): Int{
        var state = STATE_NONE
        if (item.isPlayable){
            state = STATE_PLAYABLE
            if (MediaIDHelper.isMediaItemPlaying(context as Activity, item)){
                state = getStateFromController(context)
            }
        }
        return state
    }

    private fun getStateFromController(context: Activity): Int {
        val controller = MediaControllerCompat.getMediaController(context)
        val pbState = controller.playbackState
        return if (pbState == null || pbState.state == PlaybackStateCompat.STATE_ERROR) {
            STATE_NONE
        } else if (pbState.state == PlaybackStateCompat.STATE_PLAYING) {
            STATE_PLAYING
        } else {
            STATE_PAUSED
        }
    }

    private fun getDrawableByState(context: Context, state: Int): Drawable? {
        val sColorStateNotPlaying = ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.media_item_icon_not_playing))
        val sColorStatePlaying = ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.media_item_icon_playing))
        when (state) {
            STATE_PLAYABLE -> {
                val pauseDrawable = ContextCompat.getDrawable(context,
                        R.mipmap.ic_play_arrow_black_36dp)
                DrawableCompat.setTintList(pauseDrawable!!, sColorStateNotPlaying)
                return pauseDrawable
            }
            STATE_PLAYING -> {
                val animation = ContextCompat.getDrawable(context, R.drawable.ic_equalizer_white_36dp) as AnimationDrawable?
                DrawableCompat.setTintList(animation!!, sColorStatePlaying)
                animation.start()
                return animation
            }
            STATE_PAUSED -> {
                val playDrawable = ContextCompat.getDrawable(context, R.mipmap.ic_equalizer1_white_36dp)
                DrawableCompat.setTintList(playDrawable!!, sColorStatePlaying)
                return playDrawable
            }
            else -> return null
        }
    }


}