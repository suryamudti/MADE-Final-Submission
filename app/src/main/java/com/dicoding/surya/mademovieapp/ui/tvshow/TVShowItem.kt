package com.dicoding.surya.mademovieapp.ui.tvshow

import android.content.Intent
import com.bumptech.glide.Glide
import com.dicoding.surya.mademovieapp.R
import com.dicoding.surya.mademovieapp.data.models.TVShow
import com.dicoding.surya.mademovieapp.databinding.TvShowItemBinding
import com.dicoding.surya.mademovieapp.ui.tvshow.detail.TVShowDetailActivity
import com.dicoding.surya.mademovieapp.utils.Constants
import com.xwray.groupie.databinding.BindableItem
import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * Created by suryamudti on 27/08/2019.
 */
class TVShowItem(
    private val tvShow : TVShow
): BindableItem<TvShowItemBinding>()  {
    override fun getLayout() = R.layout.tv_show_item

    override fun bind(viewBinding: TvShowItemBinding, position: Int) {

        Glide.with(viewBinding.root.context).load("https://image.tmdb.org/t/p/w342"+tvShow.backdrop_path).into(viewBinding.imgPoster)

        viewBinding.tvShow = tvShow

        viewBinding.root.setOnClickListener {
            val intent = Intent(it.context, TVShowDetailActivity::class.java )
            intent.putExtra(Constants.TV_SHOW_ID_EXTRAS, tvShow)
            it.context.startActivity(intent)
        }

        val getDate : String? = tvShow.first_air_date

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