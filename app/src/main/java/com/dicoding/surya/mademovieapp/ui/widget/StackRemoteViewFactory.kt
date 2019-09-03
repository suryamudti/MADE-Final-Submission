package com.dicoding.surya.mademovieapp.ui.widget

import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.dicoding.surya.mademovieapp.R
import com.dicoding.surya.mademovieapp.data.db.MyDatabase
import com.dicoding.surya.mademovieapp.data.models.Movie
import com.dicoding.surya.mademovieapp.utils.Coroutines

/**
 * Created by suryamudti on 29/08/2019.
 */
class StackRemoteViewFactory (val context: Context) : RemoteViewsService.RemoteViewsFactory {

    private lateinit var db : MyDatabase

    private val mWidgetItems = ArrayList<Movie>()

    override fun onCreate() {
        db = MyDatabase(context)
        Coroutines.io {
            mWidgetItems.addAll(db.getFavoriteMovieDao().getMoviesFromWidget())
        }
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun onDataSetChanged() {

        val identityToken = Binder.clearCallingIdentity()

        mWidgetItems.clear()
        mWidgetItems.addAll(db.getFavoriteMovieDao().getMoviesFromWidget())

        Binder.restoreCallingIdentity(identityToken)
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(context.packageName, R.layout.widget_item)
        var poster = Glide.with(context)
            .asBitmap()
            .load("https://image.tmdb.org/t/p/w342" + mWidgetItems[position].poster_path)
            .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
            .get()

        rv.setImageViewBitmap(R.id.imageView, poster)
        val extras = Bundle()
        extras.putInt(FavoriteMovieWidget().EXTRA_ITEM, position)
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
    }

    override fun getCount(): Int {
        return mWidgetItems.size
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun onDestroy() {

    }

}