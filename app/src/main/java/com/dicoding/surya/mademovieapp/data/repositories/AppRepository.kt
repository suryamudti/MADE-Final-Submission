package com.dicoding.surya.mademovieapp.data.repositories

import com.dicoding.surya.mademovieapp.data.db.MyDatabase
import com.dicoding.surya.mademovieapp.data.models.Movie
import com.dicoding.surya.mademovieapp.data.models.TVShow
import com.dicoding.surya.mademovieapp.data.network.MyApi
import com.dicoding.surya.mademovieapp.data.network.SafeApiRequest
import com.dicoding.surya.mademovieapp.data.network.responses.MoviesResponse
import com.dicoding.surya.mademovieapp.data.network.responses.TVShowResponse
import com.dicoding.surya.mademovieapp.data.preferences.PreferenceProvider

/**
 * Created by suryamudti on 27/08/2019.
 */
class AppRepository(
    private val api : MyApi,
    private val db : MyDatabase,
    private val prefs: PreferenceProvider
): SafeApiRequest() {

    // API
    suspend fun getMovies() : MoviesResponse{
        return apiRequest {
            api.getMovies()
        }
    }

    suspend fun getTVShows() : TVShowResponse{
        return apiRequest {
            api.getTVShows()
        }
    }

    suspend fun searchMovie(query : String) : MoviesResponse{
        return apiRequest {
            api.searchMovie(query)
        }
    }

    suspend fun searchTVShow(query : String) : TVShowResponse{
        return apiRequest {
            api.searchTVShow(query)
        }
    }


    // local Movies
    suspend fun saveMovies(movie: Movie){
        db.getFavoriteMovieDao().insertMovie(movie)
    }

    fun deleteMovie(movie: Movie){
        db.getFavoriteMovieDao().deleteMovies(movie)
    }

    fun getAllMovies() = db.getFavoriteMovieDao().getAllMovies()

    fun getSingleMovie(id : Int) = db.getFavoriteMovieDao().getSingleMovie(id)


    // local TV SHow
    suspend fun saveTVShow(tvShow: TVShow){
        db.getFavoriteTVShowDao().insertTVShow(tvShow)
    }

    fun getAllTVShow() = db.getFavoriteTVShowDao().getAllTVShows()

    fun getSingleTVShow(id : Int) = db.getFavoriteTVShowDao().getSingleTVShow(id)

    fun deleteTVShow(tvShow: TVShow){
        db.getFavoriteTVShowDao().deleteTVShows(tvShow)
    }


    // preferences
    fun setDailyReminder(daily : Boolean){
        prefs.setDailyReminder(daily)
    }

    fun isDailyReminderEnabled() : Boolean{
        return prefs.isDailyReminderEnabled()
    }

    fun setReleaseReminder(release : Boolean){
        return prefs.setReleaseReminder(release)
    }

    fun isReleaseReminderEnabled() : Boolean{
        return prefs.isReleaseReminderEnabled()
    }





}