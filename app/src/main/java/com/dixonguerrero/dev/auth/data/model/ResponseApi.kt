package com.dixonguerrero.dev.auth.data.model

import com.google.gson.annotations.SerializedName

data class ResponseApi(
    @SerializedName("message") val message: String?,
    @SerializedName("error") val error: String?
)
