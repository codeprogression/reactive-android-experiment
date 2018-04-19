package com.codeprogression.rad.movies.service

import com.codeprogression.rad.BuildConfig
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

interface MoviesService {
    fun getList(page: Int): Single<MovieCollection>
}

class DefaultMoviesService : MoviesService {
    override fun getList(page: Int): Single<MovieCollection> {
        val request = Request.Builder()
                .url("https://api.themoviedb.org/3/movie/popular?api_key=${BuildConfig.TMDB_API_KEY}&page=$page")
//                .url("https://api.themoviedb.org/4/list/1")
//                .addHeader("Authorization", "Bearer ${BuildConfig.TMDB_ACCESS_TOKEN}")
                .build()

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

        val client = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()

        val gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()

        return Single.fromCallable { client.newCall(request).execute() }
                .timeout(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .map {
                    gson.fromJson(it.body()?.string(), MovieCollection::class.java)
                }
    }
}
