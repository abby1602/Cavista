package com.cavista.cavista

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    //https://api.imgur.com/3/gallery/search/1?q=vanilla
    var BASE_URL = "https://api.imgur.com/3/gallery/"

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .client(getOkHttpClient())
        .build()


    /**
     * @return OkHttp client with logging interceptor and custom timeout
     */
    private fun getOkHttpClient(): OkHttpClient {

        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .callTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .followRedirects(true)
            .retryOnConnectionFailure(true)
            .build()
    }

    /**
     * @return Singleton Retrofit instance
     */
    fun getClient(): Retrofit {
        return retrofit
    }
}