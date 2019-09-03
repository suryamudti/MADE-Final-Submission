package com.dicoding.surya.mademovieapp.ui.tvshow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.surya.mademovieapp.R
import com.dicoding.surya.mademovieapp.data.models.Movie
import com.dicoding.surya.mademovieapp.data.models.TVShow
import com.dicoding.surya.mademovieapp.ui.main.MainViewModel
import com.dicoding.surya.mademovieapp.ui.main.MainViewModelFactory
import com.dicoding.surya.mademovieapp.ui.movie.MovieItem
import com.dicoding.surya.mademovieapp.utils.Constants
import com.dicoding.surya.mademovieapp.ui.tvshow.detail.TVShowDetailActivity
import com.dicoding.surya.mademovieapp.utils.toast
import com.facebook.shimmer.ShimmerFrameLayout
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_tvshow.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class TVShowFragment : Fragment(), TVShowListener,KodeinAware {

    override val kodein by kodein()

    private val factory : MainViewModelFactory by instance()

    private lateinit var mainViewModel: MainViewModel

    private lateinit var shimmer: ShimmerFrameLayout
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val viewRoot = inflater.inflate(R.layout.fragment_tvshow, container, false)
        shimmer = viewRoot.findViewById(R.id.shimmer)
        recyclerView = viewRoot.findViewById(R.id.rv_tv_show)
        recyclerView.layoutManager = GridLayoutManager(context,2)
        recyclerView.setHasFixedSize(true)

        return viewRoot
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
        mainViewModel.tvShows.observe(this, Observer {

            if (it != null){
                shimmer.visibility = View.GONE
                val mAdapter = GroupAdapter<ViewHolder>().apply {
                    addAll(it.toItem())
                }
                recyclerView.adapter = mAdapter
            }
        })

        mainViewModel.tvShowListener = this
        mainViewModel.getTVShows()
        shimmer.visibility = View.VISIBLE
    }

    override fun onStarted() {
        shimmer.visibility = View.VISIBLE
        shimmer.startShimmerAnimation()
    }

    override fun onSuccess(data: List<TVShow>) {
        shimmer.visibility = View.GONE
        val mAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(data.toItem())
        }
        recyclerView.adapter = mAdapter
    }

    override fun onFailure(message: String) {
        activity?.toast("$message")
    }

    private fun List<TVShow>.toItem() : List<TVShowItem>{
        return this.map {
            TVShowItem(it)
        }
    }


}
