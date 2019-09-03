package com.dicoding.surya.mademovieapp.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dicoding.surya.mademovieapp.data.models.TVShow

/**
 * Created by suryamudti on 27/08/2019.
 */
@Dao
interface FavoriteTvSeriesDao {

    @Insert
    suspend fun insertTVShow( tvShow: TVShow)

    @Query("SELECT * FROM  TVShow")
    fun getAllTVShows() : LiveData<List<TVShow>>

    @Query("SELECT * FROM TVShow WHERE id = :id")
    fun getSingleTVShow(id : Int) : LiveData<TVShow>

    @Delete
    fun deleteTVShows(tvShow: TVShow)
}