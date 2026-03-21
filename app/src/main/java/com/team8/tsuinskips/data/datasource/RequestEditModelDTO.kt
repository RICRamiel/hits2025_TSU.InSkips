package com.team8.tsuinskips.data.datasource

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RequestEditModelDTO(
    val startDate: String,
    val endDate: String,
    val type: MissRequestType,
    val status: String
)
