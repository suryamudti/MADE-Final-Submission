package com.dicoding.surya.mademovieapp.ui.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.surya.mademovieapp.R
import com.dicoding.surya.mademovieapp.data.models.Movie
import com.dicoding.surya.mademovieapp.data.models.TVShow
import com.dicoding.surya.mademovieapp.ui.main.MainViewModel
import com.dicoding.surya.mademovieapp.ui.main.MainViewModelFactory
import com.dicoding.surya.mademovieapp.ui.movie.MovieItem
import com.dicoding.surya.mademovieapp.ui.setting.SettingsActivity
import com.dicoding.surya.mademovieapp.ui.tvshow.TVShowItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_search_result.*
import org.kodein.di.android.kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class SearchResultActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()

    private val factory : MainViewModelFactory by instance()

    private lateinit var viewModel : MainViewModel

    private lateinit var query: String
    val EXTRA_QUERY = "extra_query"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        query = intent.getStringExtra(EXTRA_QUERY)

        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)

        val arrayAdapter = ArrayAdapter<String>(
            applicationContext, android.R.layout.simple_spinner_dropdown_item,
            resources.getStringArray(R.array.search_filter_array)
        )

        spinner_search_result.adapter = arrayAdapter

        spinner_search_result.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {


            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                progressBar_search_result.visibility = View.VISIBLE
                tv_not_found_search_result.visibility = View.GONE

                if (spinner_search_result.selectedItemPosition == 0) {
                    searchMovie()
                }
                else if (spinner_search_result.selectedItemPosition == 1) {
                    searchTVShow()
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        if (searchManager != null) {
            val searchView = (menu?.findItem(R.id.action_search_main)?.actionView) as SearchView
            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
            searchView.queryHint = resources.getString(R.string.search_hint)
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {

                    this@SearchResultActivity.query = query.toString()

                    progressBar_search_result.visibility = View.VISIBLE

//                    Toast.makeText(applicationContext, query, Toast.LENGTH_SHORT).show()
                    if (spinner_search_result.selectedItemPosition == 0) {
                        searchMovie()
                    }
                    else if (spinner_search_result.selectedItemPosition == 1) {
                        searchTVShow()
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })

        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun searchMovie(){
        viewModel.searchMovie(query)
        viewModel.movies.observe(this, Observer {
            if (it != null){
                recyclerV_search_result.layoutManager = GridLayoutManager(this,2)
                val mAdapter = GroupAdapter<ViewHolder>().apply {
                    addAll(it.toMovieItem())
                }
                recyclerV_search_result.adapter = mAdapter
                progressBar_search_result.visibility = View.GONE
            }else{
                progressBar_search_result.visibility = View.GONE
                tv_not_found_search_result.visibility = View.VISIBLE
            }
        })
    }

    private fun searchTVShow(){
        viewModel.searchTVShow(query)
        viewModel.tvShows.observe(this, Observer {
            if (it != null){
                recyclerV_search_result.layoutManager = GridLayoutManager(this,2)
                val mAdapter = GroupAdapter<ViewHolder>().apply {
                    addAll(it.toTVShowItem())
                }
                recyclerV_search_result.adapter = mAdapter
                progressBar_search_result.visibility = View.GONE
            }
        })
    }

    private fun List<TVShow>.toTVShowItem() : List<TVShowItem>{
        return this.map {
            TVShowItem(it)
        }
    }

    private fun List<Movie>.toMovieItem() : List<MovieItem>{
        return this.map {
            MovieItem(it)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("query", query)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        query = savedInstanceState?.getString("query").toString()

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_settings){
            startActivity(Intent(applicationContext, SettingsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }


}
