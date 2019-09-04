package com.surya.movieviewer.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.surya.movieviewer.R
import com.surya.movieviewer.model.Movie
import com.surya.movieviewer.ui.detail.MovieDetailActivity
import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * Created by suryamudti on 04/09/2019.
 */
class MovieAdapter(val listMovie: ArrayList<Movie>) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listMovie.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = listMovie[position]
        holder.textViewTitle.text = movie.title
        holder.textViewOverview.text = movie.vote_average

        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        try {
            val date = simpleDateFormat.parse(movie.release_date)
            val newFormat = SimpleDateFormat("EEEE, MMM dd, yyyy")
            val dateFix = newFormat.format(date)
            holder.textViewRelease.text = dateFix.split(" ")[3]
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        Glide.with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w342"+movie.poster_path)
            .into(holder.imgPoster)

        holder.cardView.setOnClickListener {
            val intent = Intent(it.context, MovieDetailActivity::class.java)
            intent.putExtra("movie",movie)
            it.context.startActivity(intent)
        }

    }

    inner class ViewHolder(view: View) :RecyclerView.ViewHolder(view){
        val textViewTitle: AppCompatTextView = view?.findViewById(R.id.tv_title)!!
        val textViewOverview: TextView = view.findViewById(R.id.tv_rating)
        val textViewRelease: TextView = view.findViewById(R.id.tv_year)
        val imgPoster: ImageView = view.findViewById(R.id.img_poster)

        val cardView: CardView  = view.findViewById(R.id.cardview_list)

    }
}