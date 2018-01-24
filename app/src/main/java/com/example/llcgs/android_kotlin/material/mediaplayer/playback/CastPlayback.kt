package com.example.llcgs.android_kotlin.material.mediaplayer.playback

import android.content.Context
import android.net.Uri
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.text.TextUtils
import com.example.llcgs.android_kotlin.material.mediaplayer.provider.MusicProvider
import com.example.llcgs.android_kotlin.material.mediaplayer.provider.MusicProviderSource
import com.example.llcgs.android_kotlin.utils.media.LogHelper
import com.example.llcgs.android_kotlin.utils.media.MediaIDHelper
import com.google.android.gms.cast.MediaInfo
import com.google.android.gms.cast.MediaMetadata
import com.google.android.gms.cast.MediaStatus
import com.google.android.gms.cast.framework.CastContext
import com.google.android.gms.cast.framework.media.RemoteMediaClient
import com.google.android.gms.common.images.WebImage

import org.json.JSONException
import org.json.JSONObject

import android.support.v4.media.session.MediaSessionCompat.QueueItem

/**
 * An implementation of Playback that talks to Cast.
 */
class CastPlayback(private val mMusicProvider: MusicProvider, context: Context) : Playback {

    private val mAppContext: Context = context.applicationContext
    private val mRemoteMediaClient: RemoteMediaClient
    private val mRemoteMediaClientListener: RemoteMediaClient.Listener

    override var state: Int = 0

    /** Playback interface Callbacks  */
    private var mCallback: Playback.Callback? = null
    private var mCurrentPosition: Long = 0
    override var currentMediaId: String = ""

    override val currentStreamPosition: Long
        get() = if (!isConnected) {
            mCurrentPosition
        } else mRemoteMediaClient.approximateStreamPosition.toInt().toLong()

    override val isConnected: Boolean
        get() {
            val castSession = CastContext.getSharedInstance(mAppContext).sessionManager
                    .currentCastSession
            return castSession != null && castSession.isConnected
        }

    override val isPlaying: Boolean
        get() = isConnected && mRemoteMediaClient.isPlaying

    init {

        val castSession = CastContext.getSharedInstance(mAppContext).sessionManager
                .currentCastSession
        mRemoteMediaClient = castSession.remoteMediaClient
        mRemoteMediaClientListener = CastMediaClientListener()
    }

    override fun start() {
        mRemoteMediaClient.addListener(mRemoteMediaClientListener)
    }

    override fun stop(notifyListeners: Boolean) {
        mRemoteMediaClient.removeListener(mRemoteMediaClientListener)
        state = PlaybackStateCompat.STATE_STOPPED
        if (notifyListeners && mCallback != null) {
            mCallback?.onPlaybackStatusChanged(state)
        }
    }

    override fun updateLastKnownStreamPosition() {
        mCurrentPosition = currentStreamPosition
    }

    override fun play(item: QueueItem) {
        try {
            loadMedia(item.description.mediaId?:"", true)
            state = PlaybackStateCompat.STATE_BUFFERING
            if (mCallback != null) {
                mCallback?.onPlaybackStatusChanged(state)
            }
        } catch (e: JSONException) {
            LogHelper.e(TAG, "Exception loading media ", e, null)
            if (mCallback != null) {
                mCallback?.onError(e.message?:"")
            }
        }

    }

    override fun pause() {
        try {
            if (mRemoteMediaClient.hasMediaSession()) {
                mRemoteMediaClient.pause()
                mCurrentPosition = mRemoteMediaClient.approximateStreamPosition.toInt().toLong()
            } else {
                loadMedia(currentMediaId, false)
            }
        } catch (e: JSONException) {
            LogHelper.e(TAG, e, "Exception pausing cast playback")
            if (mCallback != null) {
                mCallback?.onError(e.message?:"")
            }
        }

    }

    override fun seekTo(position: Long) {
        if (currentMediaId == null) {
            mCurrentPosition = position
            return
        }
        try {
            if (mRemoteMediaClient.hasMediaSession()) {
                mRemoteMediaClient.seek(position)
                mCurrentPosition = position
            } else {
                mCurrentPosition = position
                loadMedia(currentMediaId, false)
            }
        } catch (e: JSONException) {
            LogHelper.e(TAG, e, "Exception pausing cast playback")
            if (mCallback != null) {
                mCallback?.onError(e.message?:"")
            }
        }

    }

    override fun setCallback(callback: Playback.Callback) {
        this.mCallback = callback
    }

    @Throws(JSONException::class)
    private fun loadMedia(mediaId: String, autoPlay: Boolean) {
        val musicId = MediaIDHelper.extractMusicIDFromMediaID(mediaId)
        val track = mMusicProvider.getMusic(musicId) ?: throw IllegalArgumentException("Invalid mediaId " + mediaId)
        if (!TextUtils.equals(mediaId, currentMediaId)) {
            currentMediaId = mediaId
            mCurrentPosition = 0
        }
        val customData = JSONObject()
        customData.put(ITEM_ID, mediaId)
        val media = toCastMediaMetadata(track, customData)
        mRemoteMediaClient.load(media, autoPlay, mCurrentPosition, customData)
    }

    private fun setMetadataFromRemote() {
        // Sync: We get the customData from the remote media information and update the local
        // metadata if it happens to be different from the one we are currently using.
        // This can happen when the app was either restarted/disconnected + connected, or if the
        // app joins an existing session while the Chromecast was playing a queue.
        try {
            val mediaInfo = mRemoteMediaClient.mediaInfo ?: return
            val customData = mediaInfo.customData

            if (customData != null && customData.has(ITEM_ID)) {
                val remoteMediaId = customData.getString(ITEM_ID)
                if (!TextUtils.equals(currentMediaId, remoteMediaId)) {
                    currentMediaId = remoteMediaId
                    if (mCallback != null) {
                        mCallback?.setCurrentMediaId(remoteMediaId)
                    }
                    updateLastKnownStreamPosition()
                }
            }
        } catch (e: JSONException) {
            LogHelper.e(TAG, e, "Exception processing update metadata")
        }

    }

    private fun updatePlaybackState() {
        val status = mRemoteMediaClient.playerState
        val idleReason = mRemoteMediaClient.idleReason

        LogHelper.d(TAG, "onRemoteMediaPlayerStatusUpdated ", status)

        // Convert the remote playback states to media playback states.
        when (status) {
            MediaStatus.PLAYER_STATE_IDLE -> if (idleReason == MediaStatus.IDLE_REASON_FINISHED) {
                if (mCallback != null) {
                    mCallback?.onCompletion()
                }
            }
            MediaStatus.PLAYER_STATE_BUFFERING -> {
                state = PlaybackStateCompat.STATE_BUFFERING
                if (mCallback != null) {
                    mCallback?.onPlaybackStatusChanged(state)
                }
            }
            MediaStatus.PLAYER_STATE_PLAYING -> {
                state = PlaybackStateCompat.STATE_PLAYING
                setMetadataFromRemote()
                if (mCallback != null) {
                    mCallback?.onPlaybackStatusChanged(state)
                }
            }
            MediaStatus.PLAYER_STATE_PAUSED -> {
                state = PlaybackStateCompat.STATE_PAUSED
                setMetadataFromRemote()
                if (mCallback != null) {
                    mCallback?.onPlaybackStatusChanged(state)
                }
            }
            else // case unknown
            -> LogHelper.d(TAG, "State default : ", status)
        }
    }

    private inner class CastMediaClientListener : RemoteMediaClient.Listener {

        override fun onMetadataUpdated() {
            LogHelper.d(TAG, "RemoteMediaClient.onMetadataUpdated")
            setMetadataFromRemote()
        }

        override fun onStatusUpdated() {
            LogHelper.d(TAG, "RemoteMediaClient.onStatusUpdated")
            updatePlaybackState()
        }

        override fun onSendingRemoteMediaRequest() {}

        override fun onAdBreakStatusUpdated() {}

        override fun onQueueStatusUpdated() {}

        override fun onPreloadStatusUpdated() {}
    }

    companion object {

        private val TAG = LogHelper.makeLogTag(CastPlayback::class.java)
        private val MIME_TYPE_AUDIO_MPEG = "audio/mpeg"
        private val ITEM_ID = "itemId"

        /**
         * Helper method to convert a [android.media.MediaMetadata] to a
         * [com.google.android.gms.cast.MediaInfo] used for sending media to the receiver app.
         *
         * @param track [com.google.android.gms.cast.MediaMetadata]
         * @param customData custom data specifies the local mediaId used by the player.
         * @return mediaInfo [com.google.android.gms.cast.MediaInfo]
         */
        private fun toCastMediaMetadata(track: MediaMetadataCompat,
                                        customData: JSONObject): MediaInfo {
            val mediaMetadata = MediaMetadata(MediaMetadata.MEDIA_TYPE_MUSIC_TRACK)
            mediaMetadata.putString(MediaMetadata.KEY_TITLE,
                    if (track.description.title == null)
                        ""
                    else
                        track.description.title?.toString())
            mediaMetadata.putString(MediaMetadata.KEY_SUBTITLE,
                    if (track.description.subtitle == null)
                        ""
                    else
                        track.description.subtitle?.toString())
            mediaMetadata.putString(MediaMetadata.KEY_ALBUM_ARTIST,
                    track.getString(MediaMetadataCompat.METADATA_KEY_ALBUM_ARTIST))
            mediaMetadata.putString(MediaMetadata.KEY_ALBUM_TITLE,
                    track.getString(MediaMetadataCompat.METADATA_KEY_ALBUM))
            val image = WebImage(
                    Uri.Builder().encodedPath(
                            track.getString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI))
                            .build())
            // First image is used by the receiver for showing the audio album art.
            mediaMetadata.addImage(image)
            // Second image is used by Cast Companion Library on the full screen activity that is shown
            // when the cast dialog is clicked.
            mediaMetadata.addImage(image)


            return MediaInfo.Builder(track.getString(MusicProviderSource.CUSTOM_METADATA_TRACK_SOURCE))
                    .setContentType(MIME_TYPE_AUDIO_MPEG)
                    .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                    .setMetadata(mediaMetadata)
                    .setCustomData(customData)
                    .build()
        }
    }
}
