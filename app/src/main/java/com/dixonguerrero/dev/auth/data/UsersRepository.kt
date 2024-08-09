package com.dixonguerrero.dev.auth.data

import android.util.Log
import com.dixonguerrero.dev.auth.data.model.LoginModel
import com.dixonguerrero.dev.auth.data.model.ResponseApi
import com.dixonguerrero.dev.auth.data.model.UserModel
import com.dixonguerrero.dev.auth.data.model.UserProvider
import com.dixonguerrero.dev.auth.data.network.UserService

class UsersRepository {

    private val apiService = UserService()
    private val userProvider = UserProvider()

    suspend fun getOneUser(documento: String): UserModel? {
        val response = apiService.getOneUser(documento)
        userProvider.user = response!!
        return response

    }

    suspend fun updateUser(user: UserModel): ResponseApi{
        val response = apiService.updateUser(user)
        return response ?: ResponseApi(null ,error = "Algo salio mal")
    }

    suspend fun login(user: LoginModel): UserModel? {
        val response = apiService.login(user)
        userProvider.user = response
        return response ?: null

    }

    suspend fun registerUser(user: UserModel): ResponseApi?{
        val response = apiService.register(user)
        Log.i("UsersRepository", "Response: $response")
        return response ?: ResponseApi(null ,error = "El documento ya existe")

    }

}