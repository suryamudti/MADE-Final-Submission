package com.dicoding.surya.mademovieapp.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import org.json.JSONObject

/**
 * Created by suryamudti on 25/08/2019.
 */

@Entity
@Parcelize
data class Movie (
    @PrimaryKey(autoGenerate = false)
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