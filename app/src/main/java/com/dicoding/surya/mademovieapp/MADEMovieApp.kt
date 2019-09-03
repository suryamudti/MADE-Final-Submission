package com.dicoding.surya.mademovieapp

import android.app.Application
import com.dicoding.surya.mademovieapp.data.db.MyDatabase
import com.dicoding.surya.mademovieapp.data.network.MyApi
import com.dicoding.surya.mademovieapp.data.network.NetworkConnectionInterceptor
import com.dicoding.surya.mademovieapp.data.preferences.PreferenceProvider
import com.dicoding.surya.mademovieapp.data.repositories.AppRepository
import com.dicoding.surya.mademovieapp.ui.main.MainViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

/**
 * Created by suryamudti on 27/08/2019.
 */
class MADEMovieApp : Application(), KodeinAware  {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@MADEMovieApp))
        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { MyDatabase(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { AppRepository(instance(),instance(),instance()) }

        bind() from provider { MainViewModelFactory(instance()) }


    }

}