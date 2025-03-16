package com.team8.tsuinskips.data.datasource

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RequestCreateModelDTO(
    @SerializedName("startDate") val startDate: String,
    @SerializedName("endDate") val endDate: String,
    @SerializedName("type") val type: Type,
    @SerializedName("confirmationFiles") val confirmationFiles: List<Attachment>
)
