package com.example.core.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val url = "https://booking-hotel-859fa-default-rtdb.firebaseio.com/"
    private val gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()

    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

    val api : RetrofitService by lazy {
        retrofit.create(RetrofitService::class.java)
    }
}