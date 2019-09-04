package com.surya.movieviewer.db

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.surya.movieviewer.model.Movie

/**
 * Created by suryamudti on 04/09/2019.
 */
class DatabaseHelper(var context: Context)  {

    val AUTH = "content://com.dicoding.surya.mademovieapp.PROVIDER"

    fun fetchData() : ArrayList<Movie>{

        val uri : Uri = Uri.parse(AUTH)

        val contentResolver : ContentResolver = context.contentResolver

        val cursor : Cursor? = contentResolver.query(uri,null,null,null,null)

        val modelList : ArrayList<Movie> = ArrayList()

        if (cursor != null) {
            if (cursor.moveToFirst()){
                do {

                    var movie: Movie? = Movie(
                        0,
                        cursor.getString(cursor.getColumnIndexOrThrow(DBContract.MovieColumns.title)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBContract.MovieColumns.release)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBContract.MovieColumns.vote_average)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBContract.MovieColumns.description)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBContract.MovieColumns.poster)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DBContract.MovieColumns.backdrop))
                    )

                    movie?.let { modelList.add(it) }
                }while (cursor.moveToNext())
            }

            cursor.close()
            return modelList
        }

        return modelList

    }

}