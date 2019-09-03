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

        lateinit var uris: Uri
        if (context != null){
            Coroutines.io {
                val id = db.getFavoriteMovieDao().insertMovie(Movie.fromContentValues(values!!))
                if (id != null){
                    context.contentResolver?.notifyChange(uri,null)

                    return@io
                }
            }
        }

        throw IllegalArgumentException("Failed to delete row into " + uri)
//        return Uri.parse(DatabaseContract.MovieColumns().CONTENT_URI.toString()+ "/" + added)
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        lateinit var cursor: Cursor
        if (context != null){
            val id = ContentUris.parseId(uri)
            cursor = db.getFavoriteMovieDao().getAllMoviesProviderById(id.toInt())

            cursor.setNotificationUri(context.contentResolver, uri)
            return cursor
        }
        throw IllegalArgumentException("Failed to query row into " + uri)
    }



    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {

        var count : Int
        if(context != null){
//            val movie = db.getFavoriteMovieDao().getSingle(ContentUris.parseId(uri).toInt())

            count = db.getFavoriteMovieDao().update(Movie.fromContentValues(values!!))

            return count
        }

        throw IllegalArgumentException("Failed to update row into " + uri)
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {

        var count : Int

        if (context != null){
            val movie = db.getFavoriteMovieDao().getSingle(ContentUris.parseId(uri).toInt())
            count = db.getFavoriteMovieDao().deleteMovies(movie)
            context.contentResolver.notifyChange(uri,null)

            return count
        }
        throw IllegalArgumentException("Failed to delete row into " + uri)
    }

    override fun getType(uri: Uri): String? {
        return ""
    }
}