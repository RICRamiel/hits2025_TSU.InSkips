package com.team8.tsuinskips.data.datasource

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class StudentDTO(
    val id: String,
    val name: String,
    val surname: String,
    val patronymic: String,
    val groupName: String?,
    val subgroupNames: List<String>
)