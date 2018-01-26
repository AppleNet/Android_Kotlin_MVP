package com.example.llcgs.android_kotlin.material.mediaplayer.fragment

import android.app.Fragment
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.mediaplayer.base.MediaBrowserProvider
import com.example.llcgs.android_kotlin.material.mediaplayer.fragment.adapter.BrowseAdapter
import com.example.llcgs.android_kotlin.utils.itemdecoration.CustomerDecoration
import com.example.llcgs.android_kotlin.utils.media.LogHelper
import com.example.llcgs.android_kotlin.utils.media.MediaIDHelper
import com.example.llcgs.android_kotlin.utils.media.NetworkHelper
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * com.example.llcgs.android_kotlin.material.mediaplayer.playback.fragment.MediaBrowserFragment
 * @author liulongchao
 * @since 2018/1/24
 */
class MediaBrowserFragment: Fragment(), BaseQuickAdapter.OnItemClickListener {

    private var mMediaId: String? = null
    private var mMediaFragmentListener: MediaFragmentListener?= null
    private lateinit var adapter: BrowseAdapter

    private val mConnectivityChangeReceiver = object : BroadcastReceiver(){
        var oldOnline = false
        override fun onReceive(context: Context, intent: Intent) {
            if (mMediaId != null){
                val isOnline = NetworkHelper.isOnline(context)
                if (isOnline != oldOnline){
                    oldOnline = isOnline
                    checkForUserVisibleErrors(false)
                    if (isOnline) {
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    private val mMediaControllerCallback= object : MediaControllerCompat.Callback(){
        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            adapter.notifyDataSetChanged()
        }

        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            checkForUserVisibleErrors(false)
            adapter.notifyDataSetChanged()
        }
    }

    private val mSubscriptionCallback = object: MediaBrowserCompat.SubscriptionCallback(){
        override fun onChildrenLoaded(parentId: String, children: MutableList<MediaBrowserCompat.MediaItem>) {
            checkForUserVisibleErrors(children.isEmpty())
            adapter.data.clear()
            adapter.setNewData(children)
            adapter.notifyDataSetChanged()
        }

        override fun onError(parentId: String) {
            checkForUserVisibleErrors(true)
            Toast.makeText(activity, "Error Loading Media", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mMediaFragmentListener = activity as MediaFragmentListener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_list, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        adapter = BrowseAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)
        adapter.onItemClickListener = this

    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        checkForUserVisibleErrors(false)
        val item: MediaBrowserCompat.MediaItem = adapter.getItem(position) as MediaBrowserCompat.MediaItem
        mMediaFragmentListener?.onMediaItemSelected(item = item)
    }

    override fun onStart() {
        super.onStart()
        val mediaBrowser = mMediaFragmentListener?.getMediaBrowser()
        if (mediaBrowser?.isConnected == true){
            onConnected()
        }
        activity?.registerReceiver(mConnectivityChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onStop() {
        super.onStop()
        val mediaBrowser = mMediaFragmentListener?.getMediaBrowser()
        if (mediaBrowser != null && mediaBrowser.isConnected && mMediaId != null) {
            mediaBrowser.unsubscribe(mMediaId?:"Kotlin")
        }
        val controller = MediaControllerCompat.getMediaController(activity)
        controller?.unregisterCallback(mMediaControllerCallback)
        activity?.unregisterReceiver(mConnectivityChangeReceiver)
    }

    override fun onDetach() {
        super.onDetach()
        mMediaFragmentListener = null
    }

    fun onConnected(){
        if (isDetached) {
            return
        }
        mMediaId = getMediaId()
        if (mMediaId == null){
            mMediaId = mMediaFragmentListener?.getMediaBrowser()?.root
        }
        updateTitle()
        mMediaFragmentListener?.getMediaBrowser()?.unsubscribe(mMediaId?:"Kotlin")
        mMediaFragmentListener?.getMediaBrowser()?.subscribe(mMediaId?:"Kotlin", mSubscriptionCallback)
        val controller = MediaControllerCompat.getMediaController(activity)
        controller?.registerCallback(mMediaControllerCallback)
    }

    fun checkForUserVisibleErrors(forceError: Boolean){
        var showError = forceError
        if (!NetworkHelper.isOnline(activity)) {
            error_message.text = "Cannot connect to server. Please, check your Internet connectivity."
            showError = true
        } else {
            val controller = MediaControllerCompat.getMediaController(activity)
            if (controller != null
                    && controller.metadata != null
                    && controller.playbackState != null
                    && controller.playbackState.state == PlaybackStateCompat.STATE_ERROR
                    && controller.playbackState.errorMessage != null) {
                error_message.text = controller.playbackState.errorMessage
                showError = true
            } else if (forceError) {
                error_message.text = "Error Loading Media"
                showError = true
            }
        }
        playback_error.visibility = if (showError) View.VISIBLE else View.GONE
    }

    private fun updateTitle(){
        if (MediaIDHelper.MEDIA_ID_ROOT == mMediaId){
            mMediaFragmentListener?.setToolbarTitle("Kotlin")
            return
        }
        val mediaBrowser = mMediaFragmentListener?.getMediaBrowser()
        mMediaId = if (mMediaId?.isNotEmpty() == true) mMediaId else "Kotlin"
        mediaBrowser?.getItem(mMediaId?:"Kotlin", object: MediaBrowserCompat.ItemCallback(){
            override fun onItemLoaded(item: MediaBrowserCompat.MediaItem?) {
                mMediaFragmentListener?.setToolbarTitle(item?.description?.title)
            }
        })
    }

    fun setMediaId(mediaId: String?) {
        val args = Bundle(1)
        args.putString(ARG_MEDIA_ID, mediaId)
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
        val TAG = LogHelper.makeLogTag(MediaBrowserFragment::class.java)
    }
}