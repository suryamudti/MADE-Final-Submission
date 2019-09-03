package com.dicoding.surya.mademovieapp.data.network.responses

import com.dicoding.surya.mademovieapp.data.models.Movie

/**
 * Created by suryamudti on 27/08/2019.
 */
data class MoviesResponse(
    val results : List<Movie>
)