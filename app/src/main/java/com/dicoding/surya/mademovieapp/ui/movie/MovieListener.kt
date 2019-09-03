package com.dicoding.surya.mademovieapp.ui.movie

import com.dicoding.surya.mademovieapp.data.models.Movie

/**
 * Created by suryamudti on 27/08/2019.
 */
interface MovieListener {
    fun onStarted()
    fun onSuccess(data: List<Movie>)
    fun onFailure(message: String)
}