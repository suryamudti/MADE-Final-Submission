package com.surya.movieviewer.ui

import android.content.Intent
import com.bumptech.glide.Glide
import com.surya.movieviewer.R
import com.surya.movieviewer.databinding.ListItemBinding
import com.surya.movieviewer.model.Movie
import com.surya.movieviewer.ui.detail.MovieDetailActivity
import com.xwray.groupie.databinding.BindableItem
import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * Created by suryamudti on 27/08/2019.
 */
class MovieItem(
    private val movie: Movie
) : BindableItem<ListItemBinding>(){
    override fun getLayout() = R.layout.list_item

    override fun bind(viewBinding: ListItemBinding, position: Int) {

        Glide.with(viewBinding.root.context).load("https://image.tmdb.org/t/p/w342"+movie.backdrop_path).into(viewBinding.imgPoster)

        viewBinding.movie = movie

        viewBinding.root.setOnClickListener {
            val intent = Intent(it.context, MovieDetailActivity::class.java)
            intent.putExtra("movie",movie)
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