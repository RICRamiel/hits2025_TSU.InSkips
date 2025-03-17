package com.team8.tsuinskips.data.datasource

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RequestDTO(
    @SerializedName("id") val id: String,
    @SerializedName("startDate") val startDate: String,
    @SerializedName("endDate") val endDate: String,
    @SerializedName("creator") val creator: StudentDTO,
    @SerializedName("type") val missRequestType: MissRequestType,
    @SerializedName("status") val status: Status,
    @SerializedName("confirmationFiles") val confirmationFiles: List<ConfirmationFileDTO>
)
