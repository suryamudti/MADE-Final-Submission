package com.surya.movieviewer
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

/**
 * Created by suryamudti on 03/09/2019.
 */
class DBContract {



    class MovieColums: BaseColumns{
        companion object{
            val title = "title"
            val description = "overview"
            val release = "release_date"
            val poster = "image_poster"
        }
    }

    companion object{
        val AUTHORITY = "com.dicoding.surya.mademovieapp"

        val TABLE_NAME = "Movie"

        val CONTENT_URI : Uri = Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()

        fun getColumnInt(cursor:Cursor, columnName: String) : Int{
            return cursor.getInt(cursor.getColumnIndex(columnName))
        }

        fun getColumnLong(cursor:Cursor, columnName: String) : Long{
            return cursor.getLong(cursor.getColumnIndex(columnName))
        }

        fun getColumnString(cursor:Cursor, columnName: String) : String{
            return cursor.getString(cursor.getColumnIndex(columnName))
        }
    }
}

