package com.team8.tsuinskips.data.datasource

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class UserRegisterDTO(
    val name: String,
    val email: String,
    val surname: String,
    val patronymic: String,
    val password: String,
)