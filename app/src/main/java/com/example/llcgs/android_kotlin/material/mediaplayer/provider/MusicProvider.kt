package com.example.llcgs.android_kotlin.material.mediaplayer.provider

import android.content.res.Resources
import android.graphics.Bitmap
import android.net.Uri
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import com.example.llcgs.android_kotlin.utils.media.LogHelper
import com.example.llcgs.android_kotlin.utils.media.MediaIDHelper
import com.example.llcgs.android_kotlin.utils.media.MediaIDHelper.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * com.example.llcgs.android_kotlin.material.mediaplayer.provider.MusicProvider
 * @author liulongchao
 * @since 2018/1/19
 */
class MusicProvider(private var mSource: MusicProviderSource?) {

    constructor(): this(RemoteJSONSource()){
    }

    private val TAG = LogHelper.makeLogTag(MusicProvider::class.java)

    private var mMusicListByGenre: ConcurrentHashMap<String, List<MediaMetadataCompat>> = ConcurrentHashMap()
    private var mMusicListById: ConcurrentHashMap<String, MutableMediaMetadata> = ConcurrentHashMap()
    private var mFavoriteTracks: MutableSet<String> = Collections.newSetFromMap(ConcurrentHashMap())

    internal enum class State {
        NON_INITIALIZED, INITIALIZING, INITIALIZED
    }

    @Volatile
    private var mCurrentState = State.NON_INITIALIZED

    interface Callback {
        fun onMusicCatalogReady(success: Boolean)
    }

    fun getGenres(): Iterable<String> {
        if (mCurrentState != State.INITIALIZED) {
            return Collections.emptyList()
        }
        return mMusicListByGenre.keys
    }

    fun getShuffledMusic(): Iterable<MediaMetadataCompat> {
        if (mCurrentState != State.INITIALIZED) {
            return Collections.emptyList()
        }
        val shuffled = ArrayList<MediaMetadataCompat>(mMusicListById.size)
        mMusicListById.values.forEach {
            shuffled.add(it.metadata)
        }
        Collections.shuffle(shuffled)
        return shuffled
    }

    fun getMusicsByGenre(genre: String): List<MediaMetadataCompat>? {
        return if (mCurrentState != State.INITIALIZED || !mMusicListByGenre.containsKey(genre)) {
            emptyList()
        } else mMusicListByGenre[genre]
    }

    fun searchMusicBySongTitle(query: String): List<MediaMetadataCompat> =
            searchMusic(MediaMetadataCompat.METADATA_KEY_TITLE, query)

    fun searchMusicByAlbum(query: String): List<MediaMetadataCompat> =
            searchMusic(MediaMetadataCompat.METADATA_KEY_ALBUM, query)

    fun searchMusicByArtist(query: String): List<MediaMetadataCompat> =
            searchMusic(MediaMetadataCompat.METADATA_KEY_ARTIST, query)

    fun searchMusicByGenre(query: String): List<MediaMetadataCompat> =
            searchMusic(MediaMetadataCompat.METADATA_KEY_GENRE, query)

    private fun searchMusic(metadataField: String, query: String): List<MediaMetadataCompat> {
        var query = query
        if (mCurrentState != State.INITIALIZED) {
            return emptyList()
        }
        val result = java.util.ArrayList<MediaMetadataCompat>()
        query = query.toLowerCase(Locale.getDefault())
        for (track in mMusicListById.values) {
            if (track.metadata.getString(metadataField).toLowerCase(Locale.US)
                            .contains(query)) {
                result.add(track.metadata)
            }
        }
        return result
    }

    fun getMusic(musicId: String): MediaMetadataCompat? =
            if (mMusicListById.containsKey(musicId)) mMusicListById[musicId]?.metadata else null

    @Synchronized
    fun updateMusicArt(musicId: String, albumArt: Bitmap, icon: Bitmap) {
        var metadata = getMusic(musicId)
        metadata = MediaMetadataCompat.Builder(metadata)
                .putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, albumArt)
                .putBitmap(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON, icon)
                .build()
        val mutableMetadata = mMusicListById[musicId] ?: throw IllegalStateException("Unexpected error: Inconsistent data structures in " + "MusicProvider")
        mutableMetadata.metadata = metadata
    }

    fun setFavorite(musicId: String, favorite: Boolean){
        if (favorite) {
            mFavoriteTracks.add(musicId)
        } else {
            mFavoriteTracks.remove(musicId)
        }
    }

    fun isInitialized(): Boolean = mCurrentState == State.INITIALIZED

    fun isFavorite(musicId: String): Boolean = mFavoriteTracks.contains(musicId)

    fun retrieveMediaAsync(callback: Callback?){
        if (mCurrentState == State.INITIALIZED) {
            callback?.onMusicCatalogReady(true)
            return
        }
        //
        Observable.just(mCurrentState)
                .flatMap {
                    val retrieveMedia = retrieveMedia()
                    Observable.just(retrieveMedia)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    callback?.onMusicCatalogReady(it == State.INITIALIZED)
                }

    }

    @Synchronized
    private fun retrieveMedia(): State {
        try {
            if (mCurrentState == State.NON_INITIALIZED) {
                mCurrentState = State.INITIALIZING

                val tracks = mSource?.iterator()
                while (tracks?.hasNext() == true) {
                    val item = tracks.next()
                    val musicId = item.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID)
                    mMusicListById[musicId!!] = MutableMediaMetadata(item, musicId)
                }
                buildListsByGenre()
                mCurrentState = State.INITIALIZED
            }
        } finally {
            if (mCurrentState != State.INITIALIZED) {
                // Something bad happened, so we reset state to NON_INITIALIZED to allow
                // retries (eg if the network connection is temporary unavailable)
                mCurrentState = State.NON_INITIALIZED
            }
        }
        return mCurrentState
    }

    @Synchronized
    private fun buildListsByGenre() {
        val newMusicListByGenre = ConcurrentHashMap<String, List<MediaMetadataCompat>>()

        for (m in mMusicListById.values) {
            val genre = m.metadata.getString(MediaMetadataCompat.METADATA_KEY_GENRE)
            var list: MutableList<MediaMetadataCompat>? = newMusicListByGenre[genre]?.toMutableList()
            if (list == null) {
                list = ArrayList()
                newMusicListByGenre[genre] = list
            }
            list.add(m.metadata)
        }
        mMusicListByGenre = newMusicListByGenre
    }

    fun getChildren(mediaId: String, resources: Resources): List<MediaBrowserCompat.MediaItem> {
        val mediaItems = ArrayList<MediaBrowserCompat.MediaItem>()

        if (!MediaIDHelper.isBrowseable(mediaId)) {
            return mediaItems
        }

        if (MEDIA_ID_ROOT.equals(mediaId)) {
            mediaItems.add(createBrowsableMediaItemForRoot(resources))

        } else if (MEDIA_ID_MUSICS_BY_GENRE.equals(mediaId)) {
            for (genre in getGenres()) {
                mediaItems.add(createBrowsableMediaItemForGenre(genre, resources))
            }

        } else if (mediaId.startsWith(MEDIA_ID_MUSICS_BY_GENRE)) {
            val genre = MediaIDHelper.getHierarchy(mediaId)[1]
            for (metadata in getMusicsByGenre(genre)?: ArrayList()) {
                mediaItems.add(createMediaItem(metadata))
            }

        } else {
            LogHelper.w(TAG, "Skipping unmatched mediaId: ", mediaId)
        }
        return mediaItems
    }

    private fun createBrowsableMediaItemForRoot(resources: Resources): MediaBrowserCompat.MediaItem {
        val description = MediaDescriptionCompat.Builder()
                .setMediaId(MEDIA_ID_MUSICS_BY_GENRE)
                .setTitle("Genres")
                .setSubtitle("Songs by genre")
                .setIconUri(Uri.parse("android.resource://" + "com.example.android.uamp/drawable/ic_by_genre"))
                .build()
        return MediaBrowserCompat.MediaItem(description,
                MediaBrowserCompat.MediaItem.FLAG_BROWSABLE)
    }

    private fun createBrowsableMediaItemForGenre(genre: String,
                                                 resources: Resources): MediaBrowserCompat.MediaItem {
        val description = MediaDescriptionCompat.Builder()
                .setMediaId(createMediaID(null, MEDIA_ID_MUSICS_BY_GENRE, genre))
                .setTitle(genre)
                .setSubtitle("$genre s songs")
                .build()
        return MediaBrowserCompat.MediaItem(description,
                MediaBrowserCompat.MediaItem.FLAG_BROWSABLE)
    }

    private fun createMediaItem(metadata: MediaMetadataCompat): MediaBrowserCompat.MediaItem {
        val genre = metadata.getString(MediaMetadataCompat.METADATA_KEY_GENRE)
        val hierarchyAwareMediaID = MediaIDHelper.createMediaID(
                metadata.description.mediaId, MEDIA_ID_MUSICS_BY_GENRE, genre)
        val copy = MediaMetadataCompat.Builder(metadata)
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, hierarchyAwareMediaID)
                .build()
        return MediaBrowserCompat.MediaItem(copy.description,
                MediaBrowserCompat.MediaItem.FLAG_PLAYABLE)

    }
}