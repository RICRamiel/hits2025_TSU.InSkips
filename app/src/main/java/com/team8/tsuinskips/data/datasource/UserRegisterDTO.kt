package com.team8.tsuinskips.data.datasource

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class UserRegisterDTO(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("surname") val surname: String,
    @SerializedName("patronymic") val patronymic: String,
    @SerializedName("password") val password: String,
)