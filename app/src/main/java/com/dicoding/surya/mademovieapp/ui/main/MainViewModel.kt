package com.dicoding.surya.mademovieapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.surya.mademovieapp.data.models.Movie
import com.dicoding.surya.mademovieapp.data.models.TVShow
import com.dicoding.surya.mademovieapp.data.repositories.AppRepository
import com.dicoding.surya.mademovieapp.ui.movie.MovieListener
import com.dicoding.surya.mademovieapp.ui.tvshow.TVShowListener
import com.dicoding.surya.mademovieapp.utils.ApiException
import com.dicoding.surya.mademovieapp.utils.Constants
import com.dicoding.surya.mademovieapp.utils.Coroutines
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception
import java.util.ArrayList

/**
 * Created by suryamudti on 25/08/2019.
 */
class MainViewModel(
    val repository: AppRepository
) : ViewModel() {

    private val listMovies = MutableLiveData<List<Movie>>()
    private val listTVShows = MutableLiveData<List<TVShow>>()

    internal val movies : LiveData<List<Movie>> get() = listMovies
    internal val tvShows : LiveData<List<TVShow>> get() = listTVShows

    var movieListener : MovieListener ?= null
    var tvShowListener : TVShowListener ?= null


    /*API*/
    fun getMovies(){
        if (listMovies.value == null){
            movieListener?.onStarted()
            Coroutines.main {
                try {
                    val response = repository.getMovies()
                    Log.e("datass", "${response.results.size}")

                    response.results.let {
                        listMovies?.value = it
                        movieListener?.onSuccess(it)
                        return@main
                    }
                }catch (e: ApiException){

                    movieListener?.onFailure(e.message!!)
                }
            }
        }
    }

    fun getTVShows(){
        if (listTVShows.value == null){
            tvShowListener?.onStarted()

            Coroutines.main{
                try {
                    val response = repository.getTVShows()

                    response.results.let {
                        listTVShows.value = it
                        tvShowListener?.onSuccess(it)
                        return@main
                    }
                }catch (e: ApiException){
                    e.message?.let { tvShowListener?.onFailure(it) }
                }
            }
        }
    }

    fun searchMovie(query : String){
        Coroutines.main {
            try {
                val response = repository.searchMovie(query)
                response.results.let {
                    listMovies.value = it
                    return@main
                }
            }catch (e : ApiException){
                Log.e("error", e.message)
            }
        }
    }

    fun searchTVShow(query: String){
        Coroutines.main {
            try {
                val response = repository.searchTVShow(query)
                response.results.let {
                    listTVShows.value = it
                    return@main
                }
            }catch (e : ApiException){
                Log.e("error", e.message)
            }
        }
    }



    /*Local Database*/

    fun getLocalMovie() = repository.getAllMovies()

    fun getSingleLocalMovie(id: Int) = repository.getSingleMovie(id)

    fun saveMovie(movie: Movie){
        Coroutines.io {
            repository.saveMovies(movie)
        }
    }

    fun deleteMovie(movie : Movie){
        Coroutines.io {
            repository.deleteMovie(movie)
        }
    }

    fun getLocalTVShow() = repository.getAllTVShow()

    fun getSingleLocalTVShow(id : Int) = repository.getSingleTVShow(id)

    fun saveTVShow(tvShow: TVShow){
        Coroutines.io {repository.saveTVShow(tvShow)}
    }

    fun deleteTVShow(tvShow: TVShow){
        Coroutines.io { repository.deleteTVShow(tvShow) }
    }



    /*Preferences*/
    fun setDailyReminder(daily : Boolean){
        repository.setDailyReminder(daily)
    }

    fun isDailyReminderEnabled() : Boolean{
        return repository.isDailyReminderEnabled()
    }

    fun setReleaseReminder(release : Boolean){
        repository.setReleaseReminder(release)
    }

    fun isReleaseReminderEnabled() : Boolean{
        return repository.isReleaseReminderEnabled()
    }


}