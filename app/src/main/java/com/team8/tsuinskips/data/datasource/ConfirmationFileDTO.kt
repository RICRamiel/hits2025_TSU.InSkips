package com.team8.tsuinskips.data.datasource

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ConfirmationFileDTO(
    val id: String,
    val name: String,
    val attachDate: String
)