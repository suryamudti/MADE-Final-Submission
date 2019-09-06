package com.dicoding.surya.mademovieapp.data.network

import com.dicoding.surya.mademovieapp.BuildConfig
import com.dicoding.surya.mademovieapp.data.network.responses.MoviesResponse
import com.dicoding.surya.mademovieapp.data.network.responses.TVShowResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by suryamudti on 06/08/2019.
 */
interface MyApi{

    @GET("discover/movie")
    suspend fun getMovies() : Response<MoviesResponse>

    @GET("discover/tv")
    suspend fun getTVShows() : Response<TVShowResponse>

    @GET("discover/movie")
    suspend fun getReleaseToday(
        @Query("primary_release_date.gte") releaseDateGte : String,
        @Query("primary_release_date.lte") releaseDateLte : String
    ) : Response<MoviesResponse>

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query : String
    ) :  Response<MoviesResponse>

    @GET("search/tv")
    suspend fun searchTVShow(
        @Query("query") query: String
    ) : Response<TVShowResponse>


    companion object{
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ) : MyApi {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BuildConfig.TMDB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }
}