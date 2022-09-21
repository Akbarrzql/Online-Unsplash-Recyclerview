package com.example.onlineapirecyclerview.Retrofit

import com.example.onlineapirecyclerview.Retrofit.ResponseUnsplash
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("search/photos?page=1&query=indonesia&client_id=Bzs6918UHXvbfwftwpa7SXclHEUKSgbtye08RZa5FIs")
    fun getData(): Call<ResponseUnsplash>
}