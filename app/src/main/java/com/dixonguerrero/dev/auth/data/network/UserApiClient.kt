package com.dixonguerrero.dev.auth.data.network

import com.dixonguerrero.dev.auth.data.model.LoginModel
import com.dixonguerrero.dev.auth.data.model.ResponseApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import com.dixonguerrero.dev.auth.data.model.UserModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Query

interface UserApiClient {

    @GET("getone")
    suspend fun getOneUser(@Query("documento") documento: String): Response<UserModel>


    @POST("login")
    suspend fun login(@Body user: LoginModel): Response<UserModel>

    @POST("create")
    suspend fun register(@Body user: UserModel): Response<ResponseApi>

    @PUT("update")
    suspend fun updateUser(@Body user: UserModel): Response<ResponseApi>


}