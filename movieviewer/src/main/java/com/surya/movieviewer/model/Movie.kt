package com.surya.movieviewer.model

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

}