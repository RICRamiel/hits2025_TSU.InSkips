package com.team8.tsuinskips.data.datasource

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class RequestCreateModelDTO(
    @SerializedName("startDate") val startDate: LocalDate,
    @SerializedName("endDate") val endDate: LocalDate,
    @SerializedName("type") val missRequestType: MissRequestType,
    @SerializedName("confirmationFiles") val confirmationFiles: List<AttachmentDto>
)
