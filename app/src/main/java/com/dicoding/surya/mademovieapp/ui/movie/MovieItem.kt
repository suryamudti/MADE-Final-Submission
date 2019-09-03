package com.dicoding.surya.mademovieapp.ui.movie

import android.content.Intent
import com.bumptech.glide.Glide
import com.dicoding.surya.mademovieapp.R
import com.dicoding.surya.mademovieapp.data.models.Movie
import com.dicoding.surya.mademovieapp.databinding.MovieItemBinding
import com.dicoding.surya.mademovieapp.ui.movie.detail.MovieDetailActivity
import com.dicoding.surya.mademovieapp.utils.Constants
import com.xwray.groupie.databinding.BindableItem
import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * Created by suryamudti on 27/08/2019.
 */
class MovieItem(
    private val movie: Movie
) : BindableItem<MovieItemBinding>(){
    override fun getLayout() = R.layout.movie_item

    override fun bind(viewBinding: MovieItemBinding, position: Int) {

        Glide.with(viewBinding.root.context).load("https://image.tmdb.org/t/p/w342"+movie.backdrop_path).into(viewBinding.imgPoster)

        viewBinding.movie = movie

        viewBinding.root.setOnClickListener {
            val intent = Intent(it.context, MovieDetailActivity::class.java)
            intent.putExtra(Constants.MOVIE_ID_EXTRAS,movie)
            it.context.startActivity(intent)
        }

        val getDate : String? = movie.release_date

        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        try {
            val date = simpleDateFormat.parse(getDate)
            val newFormat = SimpleDateFormat("EEEE, MMM dd, yyyy")
            val dateFix = newFormat.format(date)
            viewBinding.tvYear.text = dateFix.split(" ")[3]
        } catch (e: ParseException) {
            e.printStackTrace()
        }


    }
}