package com.team8.tsuinskips.data.datasource

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class StudentDTO(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("surname") val surname: String,
    @SerializedName("patronymic") val patronymic: String,
    @SerializedName("groupName") val groupName: String,
    @SerializedName("subgroupNames") val subgroupNames: List<String>
)