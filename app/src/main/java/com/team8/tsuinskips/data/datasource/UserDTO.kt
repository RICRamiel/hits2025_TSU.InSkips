package com.team8.tsuinskips.data.datasource

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("surname") val surname: String,
    @SerializedName("patronymic") val patronymic: String,
    @SerializedName("roles") val roles: List<Roles>
)
