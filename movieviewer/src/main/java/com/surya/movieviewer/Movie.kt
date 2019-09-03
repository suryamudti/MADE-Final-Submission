package com.surya.movieviewer

import android.content.ContentValues
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.json.JSONObject

/**
 * Created by suryamudti on 25/08/2019.
 */

@Parcelize
data class Movie (
    var id: Int,
    var title: String,
    var release_date: String,
    var vote_average: String,
    var overview: String,
    var poster_path: String,
    var backdrop_path: String) : Parcelable {

    constructor(`object`: JSONObject) : this(
        `object`.getInt("id"),
        `object`.getString("title"),
        `object`.getString("release_date"),
        `object`.getString("vote_average"),
        `object`.getString("overview"),
        `object`.getString("poster_path"),
        `object`.getString("backdrop_path")
    )

    companion object{

        fun fromContentValues(values: ContentValues) : Movie {
            lateinit var movie : Movie

            if (values.containsKey("title")) movie.title = values.getAsString("title")
            if (values.containsKey("id")) movie.id = values.getAsInteger("id")
            if (values.containsKey("release_date")) movie.release_date = values.getAsString("release_date")
            if (values.containsKey("vote_average")) movie.vote_average = values.getAsString("vote_average")
            if (values.containsKey("overview")) movie.overview = values.getAsString("overview")
            if (values.containsKey("poster_path")) movie.release_date = values.getAsString("poster_path")
            if (values.containsKey("backdrop_path")) movie.release_date = values.getAsString("backdrop_path")


            return movie
        }
    }



}