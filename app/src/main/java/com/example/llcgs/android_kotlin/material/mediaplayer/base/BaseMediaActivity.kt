package com.example.llcgs.android_kotlin.material.mediaplayer.base

import android.app.ActivityManager
import android.content.ComponentName
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.RemoteException
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.base.BaseMaterialPresenter
import com.example.llcgs.android_kotlin.material.mediaplayer.playback.fragment.PlaybackControlsFragment
import com.example.llcgs.android_kotlin.material.mediaplayer.service.MusicService
import com.example.llcgs.android_kotlin.utils.media.LogHelper
import com.example.llcgs.android_kotlin.utils.media.ResourceHelper

/**
 * com.example.llcgs.android_kotlin.material.mediaplayer.base.BaseMediaActivity
 * @author liulongchao
 * @since 2018/1/19
 */
abstract class BaseMediaActivity<P: BaseMaterialPresenter>: ActionBarCastActivity<P>(), MediaBrowserProvider {

    private val TAG = BaseMediaActivity::class.java.simpleName
    private lateinit var mMediaBrowser: MediaBrowserCompat
    private var mControlsFragment: PlaybackControlsFragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 21){
            val taskDesc = ActivityManager.TaskDescription(
                    title.toString(),
                    BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher_white),
                    ResourceHelper.getThemeColor(this, R.attr.colorPrimary, android.R.color.darker_gray))
            setTaskDescription(taskDesc)
        }

        mMediaBrowser = MediaBrowserCompat(this, ComponentName(this, MusicService::class.java), mConnectionCallback, null)
    }

    override fun onStart() {
        super.onStart()
        mControlsFragment = fragmentManager.findFragmentById(R.id.fragment_playback_controls) as PlaybackControlsFragment?
        hidePlaybackControls()
        mMediaBrowser.connect()
    }

    override fun onStop() {
        super.onStop()
        val controllerCompat = MediaControllerCompat.getMediaController(this)
        controllerCompat?.unregisterCallback(mMediaControllerCallback)
        mMediaBrowser.disconnect()
    }

    override fun getMediaBrowser(): MediaBrowserCompat = mMediaBrowser

    open fun onMediaControllerConnected(){

    }

    protected fun showPlaybackControls(){
        fragmentManager.beginTransaction().setCustomAnimations(R.animator.slide_in_from_bottom, R.animator.slide_out_to_bottom,
                R.animator.slide_in_from_bottom, R.animator.slide_out_to_bottom).show(mControlsFragment).commit()
    }

    protected fun hidePlaybackControls(){
        fragmentManager.beginTransaction().hide(mControlsFragment).commit()
    }

    protected fun shouldShowControls(): Boolean{
        val mediaController = MediaControllerCompat.getMediaController(this)
        if (mediaController == null || mediaController.metadata == null || mediaController.playbackState == null) {
            return false
        }
        return when (mediaController.playbackState.state) {
            PlaybackStateCompat.STATE_ERROR, PlaybackStateCompat.STATE_NONE, PlaybackStateCompat.STATE_STOPPED -> false
            else -> true
        }
    }

    @Throws(RemoteException::class)
    private fun connectToSession(token: MediaSessionCompat.Token) {
        val mediaController = MediaControllerCompat(this, token)
        MediaControllerCompat.setMediaController(this, mediaController)
        mediaController.registerCallback(mMediaControllerCallback)

        if (shouldShowControls()) {
            showPlaybackControls()
        } else {
            LogHelper.d(TAG, "connectionCallback.onConnected: " + "hiding controls because metadata is null")
            hidePlaybackControls()
        }

        if (mControlsFragment != null) {
            mControlsFragment?.onConnected()
        }

        onMediaControllerConnected()
    }

    private val mConnectionCallback = object : MediaBrowserCompat.ConnectionCallback(){
        override fun onConnected() {
            try {
                connectToSession(mMediaBrowser.sessionToken)
            } catch (e: RemoteException) {
                LogHelper.e(TAG, e, "could not connect media controller")
                hidePlaybackControls()
            }
        }
    }

    private val mMediaControllerCallback = object : MediaControllerCompat.Callback() {
        override fun onPlaybackStateChanged(state: PlaybackStateCompat) {
            if (shouldShowControls()) {
                showPlaybackControls()
            } else {
                LogHelper.d(TAG, "mediaControllerCallback.onPlaybackStateChanged: " + "hiding controls because state is ", state.state)
                hidePlaybackControls()
            }
        }

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            if (shouldShowControls()) {
                showPlaybackControls()
            } else {
                LogHelper.d(TAG, "mediaControllerCallback.onMetadataChanged: " + "hiding controls because metadata is null")
                hidePlaybackControls()
            }
        }
    }
}