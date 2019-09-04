package com.dicoding.surya.mademovieapp.data.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.dicoding.surya.mademovieapp.data.db.MyDatabase
import com.dicoding.surya.mademovieapp.data.models.Movie
import com.dicoding.surya.mademovieapp.utils.Coroutines


/**
 * Created by suryamudti on 30/08/2019.
 */
class MovieProvider : ContentProvider() {

    private val MOVIE = 1
    private val MOVIE_ID = 2
    private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    lateinit var db : MyDatabase

    val AUTHORITY = "com.dicoding.surya.mademovieapp"
    val TABLE_NAME = Movie::class.java.getSimpleName()
    val URI_MOVIE = Uri.parse("content://$AUTHORITY/$TABLE_NAME")

    override fun onCreate(): Boolean {
        db = MyDatabase(context)
        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw IllegalArgumentException("Failed to delete row into " + uri)
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {

        var cursor = db.getFavoriteMovieDao().getAllMoviesProvider()

//        cursor.setNotificationUri(context.contentResolver, uri)
        return cursor

    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {

        throw IllegalArgumentException("Failed to update row into " + uri)
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {

        throw IllegalArgumentException("Failed to delete row into " + uri)
    }

    override fun getType(uri: Uri): String? {
        throw UnsupportedOperationException("Not yet implemented")
    }
}