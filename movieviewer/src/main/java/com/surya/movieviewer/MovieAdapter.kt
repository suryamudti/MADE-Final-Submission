package com.surya.movieviewer

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.bumptech.glide.Glide

/**
 * Created by suryamudti on 04/09/2019.
 */
class MovieAdapter(context: Context?, c: Cursor?, autorecovery: Boolean) : CursorAdapter(context, c, autorecovery) {

    override fun newView(p0: Context?, p1: Cursor?, p2: ViewGroup?): View {
        val view = LayoutInflater.from(p0).inflate(R.layout.list_item,p2, false)

        return view
    }

    override fun getCursor(): Cursor {
        return super.getCursor()
    }

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {

        val textViewTitle: AppCompatTextView = view?.findViewById(R.id.tv_title)!!
        val textViewOverview: TextView = view.findViewById(R.id.tv_rating)
        val textViewRelease: TextView = view.findViewById(R.id.tv_year)
        val imgPoster: ImageView = view.findViewById(R.id.img_poster)


        Glide.with(context!!)
            .load("https://image.tmdb.org/t/p/w342"+DBContract.getColumnString(
                    cursor!!,
                    DBContract.MovieColums.poster
                )
            ).into(imgPoster)

        textViewTitle.text = DBContract.getColumnString(
            cursor,
            DBContract.MovieColums.title
        )
        textViewRelease.text = DBContract.getColumnString(
            cursor,
            DBContract.MovieColums.release
        )
        textViewOverview.text = DBContract.getColumnString(
            cursor,
            DBContract.MovieColums.description
        )




    }
}