package com.surya.movieviewer

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.ListView
import androidx.loader.content.CursorLoader
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    private lateinit var adapter: MovieAdapter

    private val LOAD_ID = 110

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var recyclerView : ListView = findViewById(R.id.rv_movie)

        adapter = MovieAdapter(this, null, true)
        recyclerView.adapter = adapter

        supportLoaderManager.initLoader(LOAD_ID,null,this)

    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return  CursorLoader(this, DBContract.CONTENT_URI,null,null,null,null)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapter.swapCursor(data)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapter.swapCursor(null)
    }

    override fun onDestroy() {
        super.onDestroy()
        supportLoaderManager.destroyLoader(LOAD_ID)
    }



}
