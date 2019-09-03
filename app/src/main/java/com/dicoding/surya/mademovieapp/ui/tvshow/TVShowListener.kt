package com.dicoding.surya.mademovieapp.ui.tvshow

import com.dicoding.surya.mademovieapp.data.models.TVShow

/**
 * Created by suryamudti on 27/08/2019.
 */
interface TVShowListener {
    fun onStarted()
    fun onSuccess(data: List<TVShow>)
    fun onFailure(message: String)
}