package com.dixonguerrero.dev.auth.domain

import com.dixonguerrero.dev.auth.data.UsersRepository
import com.dixonguerrero.dev.auth.data.model.UserModel

class GetOneUserUC {
    private val repository = UsersRepository()

    suspend operator fun invoke(documento: String): UserModel? {
        return repository.getOneUser(documento)
    }

}

