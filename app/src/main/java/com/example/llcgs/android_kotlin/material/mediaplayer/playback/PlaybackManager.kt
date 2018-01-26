/*
* Copyright (C) 2014 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.llcgs.android_kotlin.material.mediaplayer.playback

import android.content.res.Resources
import android.os.AsyncTask
import android.os.Bundle
import android.os.SystemClock
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat

import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.mediaplayer.provider.MusicProvider
import com.example.llcgs.android_kotlin.utils.media.LogHelper
import com.example.llcgs.android_kotlin.utils.media.MediaIDHelper
import com.example.llcgs.android_kotlin.utils.media.WearHelper

/**
 * Manage the interactions among the container service, the queue manager and the actual mPlayback.
 */
class PlaybackManager(private val mServiceCallback: PlaybackServiceCallback, private val mResources: Resources,
                      private val mMusicProvider: MusicProvider, private val mQueueManager: QueueManager,
                      private var mPlayback: Playback) : Playback.Callback {

    private var mMediaSessionCallback: MediaSessionCallback? = null
    private val availableActions: Long
        get() {
            var actions = PlaybackStateCompat.ACTION_PLAY_PAUSE or
                    PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID or
                    PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH or
                    PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS or
                    PlaybackStateCompat.ACTION_SKIP_TO_NEXT
            if (mPlayback.isPlaying) {
                actions = actions or PlaybackStateCompat.ACTION_PAUSE
            } else {
                actions = actions or PlaybackStateCompat.ACTION_PLAY
            }
            return actions
        }

    init {
        this.mPlayback.setCallback(this)
        this.mMediaSessionCallback = MediaSessionCallback()
    }

    fun getPlayback(): Playback = mPlayback

    fun getMediaSessionCallback(): MediaSessionCompat.Callback? = mMediaSessionCallback


    /**
     * Handle a request to play music
     */
    fun handlePlayRequest() {
        LogHelper.d(TAG, "handlePlayRequest: mState=" + mPlayback.state)
        val currentMusic = mQueueManager.currentMusic
        if (currentMusic != null) {
            mServiceCallback.onPlaybackStart()
            mPlayback.play(currentMusic)
        }
    }

    /**
     * Handle a request to pause music
     */
    fun handlePauseRequest() {
        LogHelper.d(TAG, "handlePauseRequest: mState=" + mPlayback.state)
        if (mPlayback.isPlaying) {
            mPlayback.pause()
            mServiceCallback.onPlaybackStop()
        }
    }

    /**
     * Handle a request to stop music
     *
     * @param withError Error message in case the stop has an unexpected cause. The error
     * message will be set in the PlaybackState and will be visible to
     * MediaController clients.
     */
    fun handleStopRequest(withError: String?) {
        LogHelper.d(TAG, "handleStopRequest: mState=" + mPlayback.state + " error=", withError)
        mPlayback.stop(true)
        mServiceCallback.onPlaybackStop()
        updatePlaybackState(withError)
    }


    /**
     * Update the current media player state, optionally showing an error message.
     *
     * @param error if not null, error message to present to the user.
     */
    fun updatePlaybackState(error: String?) {
        LogHelper.d(TAG, "updatePlaybackState, mPlayback state=" + mPlayback.state)
        var position = PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN
        if (mPlayback.isConnected) {
            position = mPlayback.currentStreamPosition
        }


        val stateBuilder = PlaybackStateCompat.Builder()
                .setActions(availableActions)

        setCustomAction(stateBuilder)
        var state = mPlayback.state

        // If there is an error message, send it to the mPlayback state:
        if (error != null) {
            // Error states are really only supposed to be used for errors that cause mPlayback to
            // stop unexpectedly and persist until the user takes action to fix it.
            stateBuilder.setErrorMessage(error)
            state = PlaybackStateCompat.STATE_ERROR
        }

        stateBuilder.setState(state, position, 1.0f, SystemClock.elapsedRealtime())

        // Set the activeQueueItemId if the current index is valid.
        val currentMusic = mQueueManager.currentMusic
        if (currentMusic != null) {
            stateBuilder.setActiveQueueItemId(currentMusic.queueId)
        }

        mServiceCallback.onPlaybackStateUpdated(stateBuilder.build())

        if (state == PlaybackStateCompat.STATE_PLAYING || state == PlaybackStateCompat.STATE_PAUSED) {
            mServiceCallback.onNotificationRequired()
        }
    }

    private fun setCustomAction(stateBuilder: PlaybackStateCompat.Builder) {
        val currentMusic = mQueueManager.currentMusic ?: return
        // Set appropriate "Favorite" icon on Custom action:
        val mediaId = currentMusic.description.mediaId ?: return
        val musicId = MediaIDHelper.extractMusicIDFromMediaID(mediaId)
        val favoriteIcon = if (mMusicProvider.isFavorite(musicId!!))
            R.mipmap.ic_star_on
        else
            R.mipmap.ic_star_off
        LogHelper.d(TAG, "updatePlaybackState, setting Favorite custom action of music ",
                musicId, " current favorite=", mMusicProvider.isFavorite(musicId))
        val customActionExtras = Bundle()
        WearHelper.setShowCustomActionOnWear(customActionExtras, true)
        stateBuilder.addCustomAction(PlaybackStateCompat.CustomAction.Builder(
                CUSTOM_ACTION_THUMBS_UP, "favorite", favoriteIcon)
                .setExtras(customActionExtras)
                .build())
    }

    /**
     * Implementation of the Playback.Callback interface
     */
    override fun onCompletion() {
        // The media player finished playing the current song, so we go ahead
        // and start the next.
        if (mQueueManager.skipQueuePosition(1)) {
            handlePlayRequest()
            mQueueManager.updateMetadata()
        } else {
            // If skipping was not possible, we stop and release the resources:
            handleStopRequest(null)
        }
    }

    override fun onPlaybackStatusChanged(state: Int) {
        updatePlaybackState(null)
    }

    override fun onError(error: String) {
        updatePlaybackState(error)
    }

    override fun setCurrentMediaId(mediaId: String) {
        LogHelper.d(TAG, "setCurrentMediaId", mediaId)
        mQueueManager.setQueueFromMusic(mediaId)
    }


    /**
     * Switch to a different Playback instance, maintaining all mPlayback state, if possible.
     *
     * @param playback switch to this mPlayback
     */
    fun switchToPlayback(playback: Playback?, resumePlaying: Boolean) {
        if (playback == null) {
            throw IllegalArgumentException("Playback cannot be null")
        }
        // Suspends current state.
        val oldState = this.mPlayback.state
        val pos = this.mPlayback.currentStreamPosition
        val currentMediaId = this.mPlayback.currentMediaId
        this.mPlayback.stop(false)
        playback.setCallback(this)
        playback.currentMediaId = currentMediaId
        playback.seekTo(if (pos < 0) 0 else pos)
        playback.start()
        // Swaps instance.
        this.mPlayback = playback
        when (oldState) {
            PlaybackStateCompat.STATE_BUFFERING, PlaybackStateCompat.STATE_CONNECTING, PlaybackStateCompat.STATE_PAUSED -> this.mPlayback.pause()
            PlaybackStateCompat.STATE_PLAYING -> {
                val currentMusic = mQueueManager.currentMusic
                if (resumePlaying && currentMusic != null) {
                    this.mPlayback.play(currentMusic)
                } else if (!resumePlaying) {
                    this.mPlayback.pause()
                } else {
                    this.mPlayback.stop(true)
                }
            }
            PlaybackStateCompat.STATE_NONE -> {
            }
            else -> LogHelper.d(TAG, "Default called. Old state is ", oldState)
        }
    }


    private inner class MediaSessionCallback : MediaSessionCompat.Callback() {
        override fun onPlay() {
            LogHelper.d(TAG, "play")
            if (mQueueManager.currentMusic == null) {
                mQueueManager.setRandomQueue()
            }
            handlePlayRequest()
        }

        override fun onSkipToQueueItem(queueId: Long) {
            LogHelper.d(TAG, "OnSkipToQueueItem:" + queueId)
            mQueueManager.setCurrentQueueItem(queueId)
            mQueueManager.updateMetadata()
        }

        override fun onSeekTo(position: Long) {
            LogHelper.d(TAG, "onSeekTo:", position)
            mPlayback.seekTo(position.toInt().toLong())
        }

        override fun onPlayFromMediaId(mediaId: String?, extras: Bundle?) {
            LogHelper.d(TAG, "playFromMediaId mediaId:", mediaId, "  extras=", extras)
            mQueueManager.setQueueFromMusic(mediaId!!)
            handlePlayRequest()
        }

        override fun onPause() {
            LogHelper.d(TAG, "pause. current state=" + mPlayback.state)
            handlePauseRequest()
        }

        override fun onStop() {
            LogHelper.d(TAG, "stop. current state=" + mPlayback.state)
            handleStopRequest(null)
        }

        override fun onSkipToNext() {
            LogHelper.d(TAG, "skipToNext")
            if (mQueueManager.skipQueuePosition(1)) {
                handlePlayRequest()
            } else {
                handleStopRequest("Cannot skip")
            }
            mQueueManager.updateMetadata()
        }

        override fun onSkipToPrevious() {
            if (mQueueManager.skipQueuePosition(-1)) {
                handlePlayRequest()
            } else {
                handleStopRequest("Cannot skip")
            }
            mQueueManager.updateMetadata()
        }

        override fun onCustomAction(action: String, extras: Bundle?) {
            if (CUSTOM_ACTION_THUMBS_UP == action) {
                LogHelper.i(TAG, "onCustomAction: favorite for current track")
                val currentMusic = mQueueManager.currentMusic
                if (currentMusic != null) {
                    val mediaId = currentMusic.description.mediaId
                    if (mediaId != null) {
                        val musicId = MediaIDHelper.extractMusicIDFromMediaID(mediaId)
                        mMusicProvider.setFavorite(musicId!!, !mMusicProvider.isFavorite(musicId))
                    }
                }
                // mPlayback state needs to be updated because the "Favorite" icon on the
                // custom action will change to reflect the new favorite state.
                updatePlaybackState(null)
            } else {
                LogHelper.e(TAG, "Unsupported action: ", action)
            }
        }

        /**
         * Handle free and contextual searches.
         *
         *
         * All voice searches on Android Auto are sent to this method through a connected
         * [android.support.v4.media.session.MediaControllerCompat].
         *
         *
         * Threads and async handling:
         * Search, as a potentially slow operation, should run in another thread.
         *
         *
         * Since this method runs on the main thread, most apps with non-trivial metadata
         * should defer the actual search to another thread (for example, by using
         * an [AsyncTask] as we do here).
         */
        override fun onPlayFromSearch(query: String?, extras: Bundle?) {
            LogHelper.d(TAG, "playFromSearch  query=", query, " extras=", extras)

            mPlayback.state = PlaybackStateCompat.STATE_CONNECTING
            mMusicProvider.retrieveMediaAsync(object : MusicProvider.Callback {
                override fun onMusicCatalogReady(success: Boolean) {
                    if (!success) {
                        updatePlaybackState("Could not load catalog")
                    }

                    val successSearch = mQueueManager.setQueueFromSearch(query!!, extras!!)
                    if (successSearch) {
                        handlePlayRequest()
                        mQueueManager.updateMetadata()
                    } else {
                        updatePlaybackState("Could not find music")
                    }
                }
            })
        }
    }


    interface PlaybackServiceCallback {
        fun onPlaybackStart()

        fun onNotificationRequired()

        fun onPlaybackStop()

        fun onPlaybackStateUpdated(newState: PlaybackStateCompat)
    }

    companion object {

        private val TAG = LogHelper.makeLogTag(PlaybackManager::class.java)
        // Action to thumbs up a media item
        private val CUSTOM_ACTION_THUMBS_UP = "com.example.android.uamp.THUMBS_UP"
    }
}
