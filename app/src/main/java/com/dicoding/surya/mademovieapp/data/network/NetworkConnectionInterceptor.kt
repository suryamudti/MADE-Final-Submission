package com.dicoding.surya.mademovieapp.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.dicoding.surya.mademovieapp.BuildConfig
import com.dicoding.surya.mademovieapp.utils.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by suryamudti on 06/08/2019.
 */
class NetworkConnectionInterceptor(context: Context): Interceptor {

    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable()){
            throw NoInternetException("Please check the network")
        }

        val original = chain.request()
        val httpUrl = original.url()

        val newHttpUrl = httpUrl.newBuilder()
            .addQueryParameter("api_key",BuildConfig.MOVIE_API_KEY)
            .addQueryParameter("language","en-US")
            .build()
        val requestBuilder = original.newBuilder().url(newHttpUrl)


        return chain.proceed(requestBuilder.build())
    }

    private fun isInternetAvailable(): Boolean{
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.activeNetworkInfo.also {
            return it != null && it.isConnected
        }
    }

}