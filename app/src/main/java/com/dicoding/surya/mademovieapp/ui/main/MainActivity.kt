package com.dicoding.surya.mademovieapp.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.dicoding.surya.mademovieapp.ui.movie.MovieFragment
import com.dicoding.surya.mademovieapp.R
import com.dicoding.surya.mademovieapp.ui.favorite.FavoriteFragment
import com.dicoding.surya.mademovieapp.ui.home.HomeFragment
import com.dicoding.surya.mademovieapp.ui.search.SearchResultActivity
import com.dicoding.surya.mademovieapp.ui.setting.SettingsActivity
import com.dicoding.surya.mademovieapp.ui.tvshow.TVShowFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.android.kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()

    private val factory : MainViewModelFactory by instance()

    private lateinit var viewModel : MainViewModel

    private var selectedFragmentName = "home"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.elevation = 0.0f

        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)

        loadFragment(HomeFragment())

        bottom_nav_main.setOnNavigationItemSelectedListener {
            when{
                it.itemId == R.id.menu_bottom_nav_home ->
                {
                    selectedFragmentName = "home"
                    loadFragment(HomeFragment())
                }
                it.itemId == R.id.menu_bottom_nav_favorite ->
                {
                    selectedFragmentName = "favorite"
                    loadFragment(FavoriteFragment())
                }

                else ->
                {
                    selectedFragmentName = "home"
                    loadFragment(HomeFragment())
                }

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        val searchView = (menu?.findItem(R.id.action_search_main)?.actionView) as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(applicationContext, query, Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, SearchResultActivity::class.java)
                intent.putExtra(SearchResultActivity().EXTRA_QUERY, query)
                startActivity(intent)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_settings){
            startActivity(Intent(applicationContext, SettingsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadFragment(fragment: androidx.fragment.app.Fragment): Boolean {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
        return true
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.putString("selectedFragmentName", selectedFragmentName)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        selectedFragmentName = savedInstanceState?.getString("selectedFragmentName").toString()

        if (selectedFragmentName == "home"){
            loadFragment(HomeFragment())
            bottom_nav_main.selectedItemId = R.id.menu_bottom_nav_home
        }
        else if (selectedFragmentName == "favorite"){
            loadFragment(FavoriteFragment())
            bottom_nav_main.selectedItemId = R.id.menu_bottom_nav_favorite

        }
    }
}
