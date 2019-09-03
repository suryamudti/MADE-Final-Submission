package com.dicoding.surya.mademovieapp.ui.tvshow.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.dicoding.surya.mademovieapp.R
import com.dicoding.surya.mademovieapp.data.models.TVShow
import com.dicoding.surya.mademovieapp.ui.main.MainViewModel
import com.dicoding.surya.mademovieapp.ui.main.MainViewModelFactory
import com.dicoding.surya.mademovieapp.utils.Constants
import com.dicoding.surya.mademovieapp.utils.toast
import kotlinx.android.synthetic.main.activity_tvshow_detail.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class TVShowDetailActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()

    private lateinit var mainViewModel: MainViewModel

    private val factory : MainViewModelFactory by instance()

    var isFavorite = false

    private lateinit var tvShow: TVShow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvshow_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tvShow = intent.getParcelableExtra<TVShow>(Constants.TV_SHOW_ID_EXTRAS)

        tv_release_date_title_detail.text = getString(R.string.release_date)
        tv_score_title.text = getString(R.string.user_score)
        tv_description_title_detail.text = getString(R.string.overview)

        tv_title_detail.text = tvShow.name
        tv_release_date_detail.text = tvShow.first_air_date
        tv_score_value_detail.text = tvShow.vote_average
        tv_description_detail.text = tvShow.overview

        Glide.with(applicationContext).load("https://image.tmdb.org/t/p/w185" + tvShow.poster_path)
            .into(img_poster_detail)

        mainViewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)

        mainViewModel.getSingleLocalTVShow(tvShow.id).observe(this, Observer {

            if (it != null){
                isFavorite = true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.menu_add_to_favorite -> {
                if (isFavorite){
                    mainViewModel.deleteTVShow(tvShow)
                    isFavorite = false
                    toast(getString(R.string.remove_tv_from_favorite_success))
                    invalidateOptionsMenu()
                }else{
                    mainViewModel.saveTVShow(tvShow)
                    isFavorite = true
                    toast(getString(R.string.add_tv_show_to_favorite_success))
                    invalidateOptionsMenu()
                }

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.getItem(0)?.isVisible = true

        if (isFavorite)
        {
            //menu?.getItem(0)?.isVisible = true
            menu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_white_24dp)
        }else{
            //menu?.getItem(0)?.isVisible = true
            menu?.getItem(0)?.icon = ContextCompat
                .getDrawable(this, R.drawable.ic_favorite_border_white_24dp)

        }
        return super.onPrepareOptionsMenu(menu)
    }
}
