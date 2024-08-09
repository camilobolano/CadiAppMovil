package com.dixonguerrero.dev.auth.domain

import com.dixonguerrero.dev.auth.data.UsersRepository
import com.dixonguerrero.dev.auth.data.model.LoginModel
import com.dixonguerrero.dev.auth.data.model.UserModel

class LoginUC {
    private val repository = UsersRepository()

    suspend operator fun invoke(user: LoginModel): UserModel? {
        return repository.login(user)
    }

}