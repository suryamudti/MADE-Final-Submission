package com.dicoding.surya.mademovieapp.data.db

import android.provider.BaseColumns
import android.database.Cursor
import android.net.Uri

class DatabaseContract {
    var TABLE_MOVIE = "movie"
    var TABLE_TV_SHOW = "tv_show"

    val AUTHORITY = "com.imamfrf.dicoding.submission5made"
    private val SCHEME = "content"


    internal class MovieColumns : BaseColumns {
        companion object {
            var ID = "id"
            var TITLE = "title"
            var RELEASE_DATE = "release_date"
            var SCORE = "score"
            var DESCRIPTION = "description"
            var POSTER = "poster"
            var BACKDROP = "backdrop"
        }

        val CONTENT_URI = Uri.Builder().scheme(DatabaseContract().SCHEME)
            .authority(DatabaseContract().AUTHORITY)
            .appendPath(DatabaseContract().TABLE_MOVIE)
            .build()
    }

    fun getColumnString(cursor: Cursor, columnName: String): String {
        return cursor.getString(cursor.getColumnIndex(columnName))
    }


    internal class TVShowColumns : BaseColumns {
        companion object {
            var ID = "id"
            var TITLE = "title"
            var FIRST_AIR_DATE ="first_air_date"
            var SCORE = "score"
            var DESCRIPTION = "description"
            var POSTER = "poster"
            var BACKDROP = "backdrop"

        }
    }
}