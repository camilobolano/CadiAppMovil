package com.dixonguerrero.dev.auth.domain

import com.dixonguerrero.dev.auth.data.UsersRepository
import com.dixonguerrero.dev.auth.data.model.ResponseApi
import com.dixonguerrero.dev.auth.data.model.UserModel

class RegisterUC {

    private val repository = UsersRepository()

    suspend operator fun invoke(user: UserModel): ResponseApi? {
        return repository.registerUser(user)
    }


}