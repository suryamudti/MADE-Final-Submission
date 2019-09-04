package com.surya.movieviewer.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.surya.movieviewer.R
import com.surya.movieviewer.model.Movie
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var movie : Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        movie = intent.getParcelableExtra<Movie>("movie")

        tv_release_date_title_detail.text = getString(R.string.release_date)
        tv_score_title.text = getString(R.string.user_score)
        tv_description_title_detail.text = getString(R.string.overview)

        tv_title_detail.text = movie.title
        tv_release_date_detail.text = movie.release_date
        tv_score_value_detail.text = movie.vote_average
        tv_description_detail.text = movie.overview

        Glide.with(this).load("https://image.tmdb.org/t/p/w185" + movie.poster_path)
            .into(img_poster_detail)


    }
}
