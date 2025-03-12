package com.team8.tsuinskips.data.datasource

import com.google.gson.annotations.SerializedName

data class UserLoginDTO(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
)
