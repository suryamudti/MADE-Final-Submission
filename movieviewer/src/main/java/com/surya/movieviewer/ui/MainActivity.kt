package com.surya.movieviewer.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.surya.movieviewer.R
import com.surya.movieviewer.db.DatabaseHelper
import com.surya.movieviewer.model.Movie
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView

    lateinit var modelList: ArrayList<Movie>

    lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rv_movie)
        recyclerView.layoutManager = GridLayoutManager(this,2)

        dbHelper = DatabaseHelper(this)

        modelList  = dbHelper.fetchData()

        if (modelList.size > 0){
            layout.visibility = View.GONE
            val mAdapter = GroupAdapter<ViewHolder>().apply {
                addAll(modelList.toItem())
            }
            recyclerView.adapter = mAdapter
        }else{
            layout.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()

        modelList.clear()
        modelList.addAll(dbHelper.fetchData())

        if (modelList.size == 0) layout.visibility = View.VISIBLE
        else layout.visibility = View.GONE

        val mAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(modelList.toItem())
        }

        recyclerView.adapter = mAdapter
    }

    private fun List<Movie>.toItem() : List<MovieItem>{
        return this.map {
            MovieItem(it)
        }
    }

}
