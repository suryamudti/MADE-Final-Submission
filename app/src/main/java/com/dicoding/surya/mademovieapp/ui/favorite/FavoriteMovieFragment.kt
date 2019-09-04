package com.dicoding.surya.mademovieapp.ui.favorite


import android.content.Context
import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.surya.mademovieapp.R
import com.dicoding.surya.mademovieapp.data.models.Movie
import com.dicoding.surya.mademovieapp.ui.main.MainViewModel
import com.dicoding.surya.mademovieapp.ui.main.MainViewModelFactory
import com.dicoding.surya.mademovieapp.ui.movie.MovieItem
import com.facebook.shimmer.ShimmerFrameLayout
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

/**
 * A simple [Fragment] subclass.
 *
 */
class FavoriteMovieFragment : Fragment(), KodeinAware {

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
        val viewRoot = inflater.inflate(R.layout.fragment_favorite_movie, container, false)

        shimmer = viewRoot.findViewById(R.id.shimmer)
        recyclerView = viewRoot.findViewById(R.id.rv_movie)

        recyclerView.layoutManager = GridLayoutManager(context,2)
        recyclerView.setHasFixedSize(true)

        return viewRoot
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)

        mainViewModel.getLocalMovie().observe(this, Observer {

            if (it != null){
                shimmer.visibility = View.GONE
                val mAdapter = GroupAdapter<ViewHolder>().apply {
                    addAll(it.toItem())
                }
                recyclerView.adapter = mAdapter
            }
        })

        shimmer.visibility = View.VISIBLE
    }

    private fun List<Movie>.toItem() : List<MovieItem>{
        return this.map {
            MovieItem(it)
        }
    }

    class DataObserver(handler: Handler, internal val context: Context) : ContentObserver(handler)


}
