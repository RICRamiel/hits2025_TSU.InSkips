package com.team8.tsuinskips.data.datasource

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RequestEditModelDTO(
    @SerializedName("startDate") val startDate: String,
    @SerializedName("endDate") val endDate: String,
    @SerializedName("type") val type: Type,
    @SerializedName("status") val status: String
)
