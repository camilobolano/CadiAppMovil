package com.dixonguerrero.dev.auth.data.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("nombre") val name: String?,
    @SerializedName("apellido") val lastName: String?,
    @SerializedName("documento") val document: String?,
    @SerializedName("usuario") val user: String?,
    @SerializedName("contrasenia") val password: String?
)