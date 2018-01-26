package com.example.llcgs.android_kotlin.material.mediaplayer.provider

import android.support.v4.media.MediaMetadataCompat
import com.example.llcgs.android_kotlin.utils.media.LogHelper
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.net.URLConnection
import java.util.ArrayList

/**
 * Utility class to get a list of MusicTrack's based on a server-side JSON
 * configuration.
 */
class RemoteJSONSource : MusicProviderSource {

    override fun iterator(): Iterator<MediaMetadataCompat> {
        try {
            val slashPos = CATALOG_URL.lastIndexOf('/')
            val path = CATALOG_URL.substring(0, slashPos + 1)
            val jsonObj = fetchJSONFromUrl(CATALOG_URL)
            val tracks = ArrayList<MediaMetadataCompat>()
            if (jsonObj != null) {
                val jsonTracks = jsonObj.getJSONArray(JSON_MUSIC)

                if (jsonTracks != null) {
                    for (j in 0 until jsonTracks.length()) {
                        tracks.add(buildFromJSON(jsonTracks.getJSONObject(j), path))
                    }
                }
            }
            return tracks.iterator()
        } catch (e: JSONException) {
            LogHelper.e(TAG, e, "Could not retrieve music list")
            throw RuntimeException("Could not retrieve music list", e)
        }

    }

    @Throws(JSONException::class)
    private fun buildFromJSON(json: JSONObject, basePath: String): MediaMetadataCompat {
        val title = json.getString(JSON_TITLE)
        val album = json.getString(JSON_ALBUM)
        val artist = json.getString(JSON_ARTIST)
        val genre = json.getString(JSON_GENRE)
        var source = json.getString(JSON_SOURCE)
        var iconUrl = json.getString(JSON_IMAGE)
        val trackNumber = json.getInt(JSON_TRACK_NUMBER)
        val totalTrackCount = json.getInt(JSON_TOTAL_TRACK_COUNT)
        val duration = json.getInt(JSON_DURATION) * 1000 // ms

        LogHelper.d(TAG, "Found music track: ", json)

        // Media is stored relative to JSON file
        if (!source.startsWith("http")) {
            source = basePath + source
        }
        if (!iconUrl.startsWith("http")) {
            iconUrl = basePath + iconUrl
        }
        // Since we don't have a unique ID in the server, we fake one using the hashcode of
        // the music source. In a real world app, this could come from the server.
        val id = source.hashCode().toString()

        // Adding the music source to the MediaMetadata (and consequently using it in the
        // mediaSession.setMetadata) is not a good idea for a real world music app, because
        // the session metadata can be accessed by notification listeners. This is done in this
        // sample for convenience only.

        return MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, id)
                .putString(MusicProviderSource.CUSTOM_METADATA_TRACK_SOURCE, source)
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, album)
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, artist)
                .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, duration.toLong())
                .putString(MediaMetadataCompat.METADATA_KEY_GENRE, genre)
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI, iconUrl)
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, title)
                .putLong(MediaMetadataCompat.METADATA_KEY_TRACK_NUMBER, trackNumber.toLong())
                .putLong(MediaMetadataCompat.METADATA_KEY_NUM_TRACKS, totalTrackCount.toLong())
                .build()
    }

    /**
     * Download a JSON file from a server, parse the content and return the JSON
     * object.
     *
     * @return result JSONObject containing the parsed representation.
     */
    @Throws(JSONException::class)
    private fun fetchJSONFromUrl(urlString: String): JSONObject? {
        var reader: BufferedReader? = null
        try {
            val urlConnection = URL(urlString).openConnection()
            reader = BufferedReader(InputStreamReader(
                    urlConnection.getInputStream(), "iso-8859-1"))
            val sb = StringBuilder()
            var line: String? = reader.readLine()
            while ((line) != null) {
                sb.append(line)
                line = reader.readLine()
            }
            return JSONObject(sb.toString())
        } catch (e: JSONException) {
            throw e
        } catch (e: Exception) {
            LogHelper.e(TAG, "Failed to parse the json for media list", e)
            return null
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    // ignore
                }
            }
        }
    }

    companion object {

        private val TAG = LogHelper.makeLogTag(RemoteJSONSource::class.java)

        protected val CATALOG_URL = "http://storage.googleapis.com/automotive-media/music.json"

        private val JSON_MUSIC = "music"
        private val JSON_TITLE = "title"
        private val JSON_ALBUM = "album"
        private val JSON_ARTIST = "artist"
        private val JSON_GENRE = "genre"
        private val JSON_SOURCE = "source"
        private val JSON_IMAGE = "image"
        private val JSON_TRACK_NUMBER = "trackNumber"
        private val JSON_TOTAL_TRACK_COUNT = "totalTrackCount"
        private val JSON_DURATION = "duration"
    }
}
