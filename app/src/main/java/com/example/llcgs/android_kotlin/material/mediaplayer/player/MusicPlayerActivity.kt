package com.example.llcgs.android_kotlin.material.mediaplayer.player

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import android.text.TextUtils
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.mediaplayer.base.BaseMediaActivity
import com.example.llcgs.android_kotlin.material.mediaplayer.fullscreen.FullScreenPlayerActivity
import com.example.llcgs.android_kotlin.material.mediaplayer.playback.fragment.MediaBrowserFragment
import com.example.llcgs.android_kotlin.material.mediaplayer.player.presenter.IMusicPlayerPresenter
import com.example.llcgs.android_kotlin.material.mediaplayer.player.presenter.impl.MusicPlayerPresenter
import com.example.llcgs.android_kotlin.utils.media.LogHelper

/**
 * com.example.llcgs.android_kotlin.material.mediaplayer.player.MusicPlayerActivity
 * @author liulongchao
 * @since 2018/1/19
 */
class MusicPlayerActivity : BaseMediaActivity<IMusicPlayerPresenter>(), MediaBrowserFragment.MediaFragmentListener {

    private var mVoiceSearchParams: Bundle? = null

    override fun createPresenter(): IMusicPlayerPresenter = MusicPlayerPresenter()

    override fun getLayoutId() = R.layout.activity_player

    override fun initViews() {
        initializeToolbar()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeFromParams(savedInstanceState, intent)
        if (savedInstanceState == null) {
            startFullScreenActivityIfNeeded(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putString(SAVED_MEDIA_ID, getMediaId())
        super.onSaveInstanceState(outState)
    }

    override fun onMediaItemSelected(item: MediaBrowserCompat.MediaItem) = when {
        item.isPlayable ->
            MediaControllerCompat.getMediaController(this@MusicPlayerActivity).transportControls.playFromMediaId(item.mediaId, null)
        item.isBrowsable -> {
            navigateToBrowser(item.mediaId ?: "")
        }
        else -> {
            LogHelper.w(TAG, "Ignoring MediaItem that is neither browsable nor playable: ",
                    "mediaId=", item.mediaId)
        }
    }

    override fun setToolbarTitle(title: CharSequence?) {
        setTitle(title)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    public fun getMediaId(): String? = getBrowseFragment().getMediaId()

    private fun getBrowseFragment(): MediaBrowserFragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG) as MediaBrowserFragment

    private fun startFullScreenActivityIfNeeded(intent: Intent) {
        if (intent.getBooleanExtra(EXTRA_START_FULLSCREEN, false)) {
            val fullScreenIntent = Intent(this, FullScreenPlayerActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    //.putExtra(EXTRA_CURRENT_MEDIA_DESCRIPTION, intent.getParcelableExtra<T>(EXTRA_CURRENT_MEDIA_DESCRIPTION))
            startActivity(fullScreenIntent)
        }
    }

    private fun initializeFromParams(savedInstanceState: Bundle?, intent: Intent) {
        var mediaId: String? = ""
        if (intent.action != null && intent.action == MediaStore.INTENT_ACTION_MEDIA_PLAY_FROM_SEARCH) {
            mVoiceSearchParams = intent.extras
        } else {
            mediaId = savedInstanceState?.getString(SAVED_MEDIA_ID)
        }
        navigateToBrowser(mediaId ?: "")
    }

    private fun navigateToBrowser(mediaId: String) {
        var fragment = getBrowseFragment()
        if (!TextUtils.equals(fragment.getMediaId(), mediaId)) {
            fragment = MediaBrowserFragment()
            fragment.setMediaId(mediaId)
            val transaction = fragmentManager.beginTransaction()
            transaction.setCustomAnimations(
                    R.animator.slide_in_from_right, R.animator.slide_out_to_left,
                    R.animator.slide_in_from_left, R.animator.slide_out_to_right)
            transaction.replace(R.id.container, fragment, FRAGMENT_TAG)
            if (mediaId.isNotEmpty()) {
                transaction.addToBackStack(null)
            }
            transaction.commit()
        }
    }

    override fun onMediaControllerConnected(){
        if (mVoiceSearchParams != null) {
            val query = mVoiceSearchParams?.getString(SearchManager.QUERY)
            MediaControllerCompat.getMediaController(this@MusicPlayerActivity).transportControls.playFromSearch(query, mVoiceSearchParams)
            mVoiceSearchParams = null
        }
        // TODO getBrowseFragment().onConnected()
    }

    companion object {
        private val TAG = LogHelper.makeLogTag(MusicPlayerActivity::class.java)
        private val SAVED_MEDIA_ID = "com.example.android.uamp.MEDIA_ID"
        private val FRAGMENT_TAG = "uamp_list_container"
        const val EXTRA_START_FULLSCREEN = "com.example.android.uamp.EXTRA_START_FULLSCREEN"
        const val EXTRA_CURRENT_MEDIA_DESCRIPTION = "com.example.android.uamp.CURRENT_MEDIA_DESCRIPTION"
    }

}