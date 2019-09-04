package com.surya.movieviewer.db
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

/**
 * Created by suryamudti on 03/09/2019.
 */


open class KBaseColumns  {
    val _ID = "_id"
}
object DBContract{
    var TABLE_MOVIE = "Movie"
    var TABLE_TV_SHOW = "tvshow"
    val AUTHORITY="com.dicoding.surya.mademovieapp"
    val SCHEME="content"
    class MovieColumns private constructor(): BaseColumns {
        companion object: KBaseColumns() {

            val title = "title"
            val description = "overview"
            val release = "release_date"
            val poster = "poster_path"
            val vote_average = "vote_average"
            val backdrop = "backdrop_path"


            val CONTENT_URI_MOVIE= Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_MOVIE)
                .build()
        }
    }

    fun getColumnString(cursor: Cursor, columnName:String):String{
        return cursor.getString(cursor.getColumnIndex(columnName))
    }

    fun getColumnInr(cursor: Cursor, columnName:String):Int{
        return cursor.getInt(cursor.getColumnIndex(columnName))
    }

    fun getColumnLong(cursor: Cursor, columnName:String):Long{
        return cursor.getLong(cursor.getColumnIndex(columnName))
    }
}

