package com.example.onlineapirecyclerview.Retrofit

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object ApiConfig {
    const val BASE_URL = "https://api.unsplash.com/"

    fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getService(): ApiService{
        return getRetrofit().create(ApiService::class.java)
    }
}