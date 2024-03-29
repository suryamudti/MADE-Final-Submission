package com.dicoding.surya.mademovieapp.data.db

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.dicoding.surya.mademovieapp.data.db.dao.FavoriteMovieDao
import com.dicoding.surya.mademovieapp.data.db.dao.FavoriteTvSeriesDao
import com.dicoding.surya.mademovieapp.data.models.Movie
import com.dicoding.surya.mademovieapp.data.models.TVShow

/**
 * Created by suryamudti on 06/08/2019.
 */


@Database(
    entities = [Movie::class, TVShow::class],
    version = 1
)
abstract class MyDatabase : RoomDatabase() {

    abstract fun getFavoriteMovieDao() : FavoriteMovieDao
    abstract fun getFavoriteTVShowDao() : FavoriteTvSeriesDao

    companion object{
        @Volatile
        private var instance: MyDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance?:buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =  Room.databaseBuilder(
            context.applicationContext,
            MyDatabase::class.java,
            "mademovie.db"
        ).build()
    }
}