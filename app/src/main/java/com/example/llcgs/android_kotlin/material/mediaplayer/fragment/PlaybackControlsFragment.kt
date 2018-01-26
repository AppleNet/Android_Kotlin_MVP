package com.example.llcgs.android_kotlin.material.mediaplayer.fragment

import android.app.Fragment
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.mediaplayer.fullscreen.FullScreenPlayerActivity
import com.example.llcgs.android_kotlin.material.mediaplayer.player.MusicPlayerActivity
import com.example.llcgs.android_kotlin.material.mediaplayer.service.AlbumArtCache
import com.example.llcgs.android_kotlin.material.mediaplayer.service.MusicService
import kotlinx.android.synthetic.main.fragment_playback_controls.*

/**
 * com.example.llcgs.android_kotlin.material.mediaplayer.playback.fragment.PlaybackControlsFragment
 * @author liulongchao
 * @since 2018/1/19
 */
class PlaybackControlsFragment: Fragment(), View.OnClickListener {

    private var mArtUrl: String = ""
    private val mCallback = object: MediaControllerCompat.Callback(){
        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            onPlaybackStateChanged(state)
        }

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            onMetadataChanged(metadata)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.fragment_playback_controls, container, false)
        rootView.setOnClickListener {
            val intent = Intent(activity, FullScreenPlayerActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            val controller = MediaControllerCompat.getMediaController(activity)
            val metadata = controller.metadata
            if (metadata != null) {
                intent.putExtra(MusicPlayerActivity.EXTRA_CURRENT_MEDIA_DESCRIPTION, metadata.description)
            }
            startActivity(intent)
        }
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        play_pause.isEnabled = true
        play_pause.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        val controller = MediaControllerCompat.getMediaController(activity)
        if (controller != null){
            onConnected()
        }
    }

    override fun onStop() {
        super.onStop()
        val controller = MediaControllerCompat.getMediaController(activity)
        if (controller != null){
            controller.unregisterCallback(mCallback)
        }
    }

    fun onConnected(){
        val controller = MediaControllerCompat.getMediaController(activity)
        onMetadataChanged(controller.metadata)
        onPlaybackStateChanged(controller.playbackState)
        controller.registerCallback(mCallback)
    }

    private fun onMetadataChanged(metadata: MediaMetadataCompat?){
        title.text = metadata?.description?.title
        artist.text = metadata?.description?.subtitle
        val artUrl = metadata?.description?.iconUri.toString()
        if (!TextUtils.equals(artUrl, mArtUrl)){
            mArtUrl = artUrl
            var art = metadata?.description?.iconBitmap
            val cache = AlbumArtCache.getInstance()
            if (art == null){
                art = cache.getIconImage(mArtUrl)
            }
            if (art != null){
                album_art.setImageBitmap(art)
            }else{
                cache.fetch(artUrl, object : AlbumArtCache.FetchListener(){
                    override fun onFetched(artUrl: String?, bigImage: Bitmap?, iconImage: Bitmap?) {
                        if (isAdded){
                            album_art.setImageBitmap(iconImage)
                        }
                    }
                })
            }
        }

    }

    private fun setExtraInfo(extraInfo: String?){
        extra_info.text = extraInfo?:""
        extra_info.visibility = if (!TextUtils.isEmpty(extraInfo)) View.VISIBLE else View.GONE
    }

    private fun onPlaybackStateChanged(state: PlaybackStateCompat?){
        var enablePlay = false
        when(state?.state){
            PlaybackStateCompat.STATE_PAUSED, PlaybackStateCompat.STATE_STOPPED ->{
                enablePlay = true
            }
            PlaybackStateCompat.STATE_ERROR ->{
                Toast.makeText(activity, state.errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
        play_pause.setImageDrawable(if (enablePlay){
            ContextCompat.getDrawable(activity, R.mipmap.ic_play_arrow_black_36dp)
        }else{
            ContextCompat.getDrawable(activity, R.mipmap.ic_pause_black_36dp)
        })
        val controller = MediaControllerCompat.getMediaController(activity)
        val castName = controller.extras.getString(MusicService.EXTRA_CONNECTED_CAST)
        val extraInfo = resources.getString(R.string.casting_to_device, castName)
        setExtraInfo(extraInfo)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.play_pause ->{
                val controller = MediaControllerCompat.getMediaController(activity)
                val stateObj = controller.playbackState
                val state = stateObj?.state ?: PlaybackStateCompat.STATE_NONE
                if (state == PlaybackStateCompat.STATE_PAUSED ||
                        state == PlaybackStateCompat.STATE_STOPPED ||
                        state == PlaybackStateCompat.STATE_NONE){
                    playMedia()
                }else if (state == PlaybackStateCompat.STATE_PLAYING ||
                        state == PlaybackStateCompat.STATE_BUFFERING ||
                        state == PlaybackStateCompat.STATE_CONNECTING){
                    pauseMedia()
                }
            }
        }
    }

    private fun playMedia() {
        val controller = MediaControllerCompat.getMediaController(activity)
        controller?.transportControls?.play()
    }

    private fun pauseMedia() {
        val controller = MediaControllerCompat.getMediaController(activity)
        controller?.transportControls?.pause()
    }
}