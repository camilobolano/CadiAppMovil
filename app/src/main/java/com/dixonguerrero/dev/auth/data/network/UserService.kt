package com.dixonguerrero.dev.auth.data.network

import android.util.Log
import com.dixonguerrero.dev.auth.core.Retrofit
import com.dixonguerrero.dev.auth.data.model.LoginModel
import com.dixonguerrero.dev.auth.data.model.ResponseApi
import com.dixonguerrero.dev.auth.data.model.UserModel

class UserService {

    private val retrofit = Retrofit.getRetrofit()

    suspend fun getOneUser(documento: String): UserModel? {
        val response = retrofit.create(UserApiClient::class.java).getOneUser(documento)
        return response.body() ?: null
    }

    suspend fun updateUser(user: UserModel): ResponseApi? {
        val response = retrofit.create(UserApiClient::class.java).updateUser(user)
        return response.body() ?: null

    }

    suspend fun login(user: LoginModel): UserModel? {
        val response = retrofit.create(UserApiClient::class.java).login(user)
        return response.body() ?: null
    }

    suspend fun register(user: UserModel): ResponseApi? {
        try {
        val response = retrofit.create(UserApiClient::class.java).register(user)

        Log.i("UsersRepository", "Response: $response")
        return response.body() ?: null
            } catch (e: Exception) {
            Log.e("UsersRepository", "Error: ${e.message}")
            return null
        }

    }

}