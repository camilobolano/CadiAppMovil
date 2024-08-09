package com.dixonguerrero.dev.auth.data.model

import com.google.gson.annotations.SerializedName

data class LoginModel(
    @SerializedName("usuario") val user: String?,
    @SerializedName("contrasenia") val password: String?
)
