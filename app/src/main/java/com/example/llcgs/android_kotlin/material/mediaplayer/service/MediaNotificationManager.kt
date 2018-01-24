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

package com.example.llcgs.android_kotlin.material.mediaplayer.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.RemoteException
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.app.NotificationCompat.MediaStyle
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.mediaplayer.player.MusicPlayerActivity
import com.example.llcgs.android_kotlin.utils.media.LogHelper
import com.example.llcgs.android_kotlin.utils.media.ResourceHelper

/**
 * Keeps track of a notification and updates it automatically for a given
 * MediaSession. Maintaining a visible notification (usually) guarantees that the music service
 * won't be killed during playback.
 */
class MediaNotificationManager @Throws(RemoteException::class) constructor(private val mService: MusicService) : BroadcastReceiver() {

    private var mSessionToken: MediaSessionCompat.Token? = null
    private var mController: MediaControllerCompat? = null
    private var mTransportControls: MediaControllerCompat.TransportControls? = null

    private var mPlaybackState: PlaybackStateCompat? = null
    private var mMetadata: MediaMetadataCompat? = null

    private var mNotificationManager: NotificationManager? = null

    private val mPlayIntent: PendingIntent
    private val mPauseIntent: PendingIntent
    private val mPreviousIntent: PendingIntent
    private val mNextIntent: PendingIntent
    private val mStopIntent: PendingIntent

    private val mStopCastIntent: PendingIntent

    private val mNotificationColor: Int

    private var mStarted = false

    private val mCb = object : MediaControllerCompat.Callback() {
        override fun onPlaybackStateChanged(state: PlaybackStateCompat) {
            mPlaybackState = state
            LogHelper.d(TAG, "Received new playback state", state)
            if (state.state == PlaybackStateCompat.STATE_STOPPED || state.state == PlaybackStateCompat.STATE_NONE) {
                stopNotification()
            } else {
                val notification = createNotification()
                if (notification != null) {
                    mNotificationManager?.notify(NOTIFICATION_ID, notification)
                }
            }
        }

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            mMetadata = metadata
            LogHelper.d(TAG, "Received new metadata ", metadata)
            val notification = createNotification()
            if (notification != null) {
                mNotificationManager?.notify(NOTIFICATION_ID, notification)
            }
        }

        override fun onSessionDestroyed() {
            super.onSessionDestroyed()
            LogHelper.d(TAG, "Session was destroyed, resetting to the new session token")
            try {
                updateSessionToken()
            } catch (e: RemoteException) {
                LogHelper.e(TAG, e, "could not connect media controller")
            }

        }
    }

    init {
        updateSessionToken()

        mNotificationColor = ResourceHelper.getThemeColor(mService, R.attr.colorPrimary,
                Color.DKGRAY)

        mNotificationManager = mService.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val pkg = mService.packageName
        mPauseIntent = PendingIntent.getBroadcast(mService, REQUEST_CODE,
                Intent(ACTION_PAUSE).setPackage(pkg), PendingIntent.FLAG_CANCEL_CURRENT)
        mPlayIntent = PendingIntent.getBroadcast(mService, REQUEST_CODE,
                Intent(ACTION_PLAY).setPackage(pkg), PendingIntent.FLAG_CANCEL_CURRENT)
        mPreviousIntent = PendingIntent.getBroadcast(mService, REQUEST_CODE,
                Intent(ACTION_PREV).setPackage(pkg), PendingIntent.FLAG_CANCEL_CURRENT)
        mNextIntent = PendingIntent.getBroadcast(mService, REQUEST_CODE,
                Intent(ACTION_NEXT).setPackage(pkg), PendingIntent.FLAG_CANCEL_CURRENT)
        mStopIntent = PendingIntent.getBroadcast(mService, REQUEST_CODE,
                Intent(ACTION_STOP).setPackage(pkg), PendingIntent.FLAG_CANCEL_CURRENT)
        mStopCastIntent = PendingIntent.getBroadcast(mService, REQUEST_CODE,
                Intent(ACTION_STOP_CASTING).setPackage(pkg),
                PendingIntent.FLAG_CANCEL_CURRENT)

        // Cancel all notifications to handle the case where the Service was killed and
        // restarted by the system.
        mNotificationManager?.cancelAll()
    }

    /**
     * Posts the notification and starts tracking the session to keep it
     * updated. The notification will automatically be removed if the session is
     * destroyed before [.stopNotification] is called.
     */
    fun startNotification() {
        if (!mStarted) {
            mMetadata = mController!!.metadata
            mPlaybackState = mController!!.playbackState

            // The notification must be updated after setting started to true
            val notification = createNotification()
            if (notification != null) {
                mController!!.registerCallback(mCb)
                val filter = IntentFilter()
                filter.addAction(ACTION_NEXT)
                filter.addAction(ACTION_PAUSE)
                filter.addAction(ACTION_PLAY)
                filter.addAction(ACTION_PREV)
                filter.addAction(ACTION_STOP_CASTING)
                mService.registerReceiver(this, filter)

                mService.startForeground(NOTIFICATION_ID, notification)
                mStarted = true
            }
        }
    }

    /**
     * Removes the notification and stops tracking the session. If the session
     * was destroyed this has no effect.
     */
    fun stopNotification() {
        if (mStarted) {
            mStarted = false
            mController!!.unregisterCallback(mCb)
            try {
                mNotificationManager?.cancel(NOTIFICATION_ID)
                mService.unregisterReceiver(this)
            } catch (ex: IllegalArgumentException) {
                // ignore if the receiver is not registered.
            }

            mService.stopForeground(true)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        LogHelper.d(TAG, "Received intent with action " + action!!)
        when (action) {
            ACTION_PAUSE -> mTransportControls!!.pause()
            ACTION_PLAY -> mTransportControls!!.play()
            ACTION_NEXT -> mTransportControls!!.skipToNext()
            ACTION_PREV -> mTransportControls!!.skipToPrevious()
            ACTION_STOP_CASTING -> {
                val i = Intent(context, MusicService::class.java)
                i.action = MusicService.ACTION_CMD
                i.putExtra(MusicService.CMD_NAME, MusicService.CMD_STOP_CASTING)
                mService.startService(i)
            }
            else -> LogHelper.w(TAG, "Unknown intent ignored. Action=", action)
        }
    }

    /**
     * Update the state based on a change on the session token. Called either when
     * we are running for the first time or when the media session owner has destroyed the session
     * (see [android.media.session.MediaController.Callback.onSessionDestroyed])
     */
    @Throws(RemoteException::class)
    private fun updateSessionToken() {
        val freshToken = mService.sessionToken
        if (mSessionToken == null && freshToken != null || mSessionToken != null && mSessionToken != freshToken) {
            if (mController != null) {
                mController!!.unregisterCallback(mCb)
            }
            mSessionToken = freshToken
            if (mSessionToken != null) {
                mController = MediaControllerCompat(mService, mSessionToken!!)
                mTransportControls = mController!!.transportControls
                if (mStarted) {
                    mController!!.registerCallback(mCb)
                }
            }
        }
    }

    private fun createContentIntent(description: MediaDescriptionCompat?): PendingIntent {
        val openUI = Intent(mService, MusicPlayerActivity::class.java)
        openUI.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        openUI.putExtra(MusicPlayerActivity.EXTRA_START_FULLSCREEN, true)
        if (description != null) {
            openUI.putExtra(MusicPlayerActivity.EXTRA_CURRENT_MEDIA_DESCRIPTION, description)
        }
        return PendingIntent.getActivity(mService, REQUEST_CODE, openUI,
                PendingIntent.FLAG_CANCEL_CURRENT)
    }

    private fun createNotification(): Notification? {
        LogHelper.d(TAG, "updateNotificationMetadata. mMetadata=" + mMetadata!!)
        if (mMetadata == null || mPlaybackState == null) {
            return null
        }

        val description = mMetadata!!.description

        var fetchArtUrl: String? = null
        var art: Bitmap? = null
        if (description.iconUri != null) {
            // This sample assumes the iconUri will be a valid URL formatted String, but
            // it can actually be any valid Android Uri formatted String.
            // async fetch the album art icon
            val artUrl = description.iconUri!!.toString()
            art = AlbumArtCache.getInstance().getBigImage(artUrl)
            if (art == null) {
                fetchArtUrl = artUrl
                // use a placeholder art while the remote art is being downloaded
                art = BitmapFactory.decodeResource(mService.resources,
                        R.mipmap.ic_default_art)
            }
        }

        // Notification channels are only supported on Android O+.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }

        val notificationBuilder = NotificationCompat.Builder(mService, CHANNEL_ID)

        val playPauseButtonPosition = addActions(notificationBuilder)
        notificationBuilder
                .setStyle(MediaStyle()
                        // show only play/pause in compact view
                        .setShowActionsInCompactView(playPauseButtonPosition)
                        .setShowCancelButton(true)
                        .setCancelButtonIntent(mStopIntent)
                        .setMediaSession(mSessionToken))
                .setDeleteIntent(mStopIntent)
                .setColor(mNotificationColor)
                .setSmallIcon(R.mipmap.ic_notification)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setOnlyAlertOnce(true)
                .setContentIntent(createContentIntent(description))
                .setContentTitle(description.title)
                .setContentText(description.subtitle)
                .setLargeIcon(art)

        if (mController != null && mController!!.extras != null) {
            val castName = mController!!.extras.getString(MusicService.EXTRA_CONNECTED_CAST)
            if (castName != null) {
                val castInfo = mService.resources
                        .getString(R.string.casting_to_device, castName)
                notificationBuilder.setSubText(castInfo)
                notificationBuilder.addAction(R.mipmap.ic_close_black_24dp,
                        mService.getString(R.string.stop_casting), mStopCastIntent)
            }
        }

        setNotificationPlaybackState(notificationBuilder)
        if (fetchArtUrl != null) {
            fetchBitmapFromURLAsync(fetchArtUrl, notificationBuilder)
        }

        return notificationBuilder.build()
    }

    private fun addActions(notificationBuilder: NotificationCompat.Builder): Int {
        LogHelper.d(TAG, "updatePlayPauseAction")

        var playPauseButtonPosition = 0
        // If skip to previous action is enabled
        if (mPlaybackState!!.actions and PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS != 0L) {
            notificationBuilder.addAction(R.mipmap.ic_skip_previous_white_24dp,
                    "Previous", mPreviousIntent)

            // If there is a "skip to previous" button, the play/pause button will
            // be the second one. We need to keep track of it, because the MediaStyle notification
            // requires to specify the index of the buttons (actions) that should be visible
            // when in compact view.
            playPauseButtonPosition = 1
        }

        // Play or pause button, depending on the current state.
        val label: String
        val icon: Int
        val intent: PendingIntent
        if (mPlaybackState!!.state == PlaybackStateCompat.STATE_PLAYING) {
            label = "Pause"
            icon = R.mipmap.uamp_ic_pause_white_24dp
            intent = mPauseIntent
        } else {
            label = "Play"
            icon = R.mipmap.uamp_ic_play_arrow_white_24dp
            intent = mPlayIntent
        }
        notificationBuilder.addAction(NotificationCompat.Action(icon, label, intent))

        // If skip to next action is enabled
        if (mPlaybackState!!.actions and PlaybackStateCompat.ACTION_SKIP_TO_NEXT != 0L) {
            notificationBuilder.addAction(R.mipmap.ic_skip_next_white_24dp,
                    "Next", mNextIntent)
        }

        return playPauseButtonPosition
    }

    private fun setNotificationPlaybackState(builder: NotificationCompat.Builder) {
        LogHelper.d(TAG, "updateNotificationPlaybackState. mPlaybackState=" + mPlaybackState!!)
        if (mPlaybackState == null || !mStarted) {
            LogHelper.d(TAG, "updateNotificationPlaybackState. cancelling notification!")
            mService.stopForeground(true)
            return
        }

        // Make sure that the notification can be dismissed by the user when we are not playing:
        builder.setOngoing(mPlaybackState!!.state == PlaybackStateCompat.STATE_PLAYING)
    }

    private fun fetchBitmapFromURLAsync(bitmapUrl: String,
                                        builder: NotificationCompat.Builder) {
        AlbumArtCache.getInstance().fetch(bitmapUrl, object : AlbumArtCache.FetchListener() {
            override fun onFetched(artUrl: String, bitmap: Bitmap, icon: Bitmap) {
                if (mMetadata != null && mMetadata!!.description.iconUri != null &&
                        mMetadata!!.description.iconUri!!.toString() == artUrl) {
                    // If the media is still the same, update the notification:
                    LogHelper.d(TAG, "fetchBitmapFromURLAsync: set bitmap to ", artUrl)
                    builder.setLargeIcon(bitmap)
                    addActions(builder)
                    mNotificationManager?.notify(NOTIFICATION_ID, builder.build())
                }
            }
        })
    }

    /**
     * Creates Notification Channel. This is required in Android O+ to display notifications.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        if (mNotificationManager?.getNotificationChannel(CHANNEL_ID) == null) {
            val notificationChannel = NotificationChannel(CHANNEL_ID, "UAMP_Channel_ID",
                    NotificationManager.IMPORTANCE_LOW)

            notificationChannel.description = "Channel ID for UAMP"

            mNotificationManager?.createNotificationChannel(notificationChannel)
        }
    }

    companion object {
        private val TAG = LogHelper.makeLogTag(MediaNotificationManager::class.java)
        private val CHANNEL_ID = "com.example.android.uamp.MUSIC_CHANNEL_ID"
        private val NOTIFICATION_ID = 412
        private val REQUEST_CODE = 100
        val ACTION_PAUSE = "com.example.android.uamp.pause"
        val ACTION_PLAY = "com.example.android.uamp.play"
        val ACTION_PREV = "com.example.android.uamp.prev"
        val ACTION_NEXT = "com.example.android.uamp.next"
        val ACTION_STOP = "com.example.android.uamp.stop"
        val ACTION_STOP_CASTING = "com.example.android.uamp.stop_cast"
    }
}
