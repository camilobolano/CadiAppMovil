package com.dixonguerrero.dev.auth.core

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:80/apimovil/index.php/user/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}