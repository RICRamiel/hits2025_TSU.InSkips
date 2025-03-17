package com.team8.tsuinskips.data.datasource

import com.google.gson.annotations.SerializedName
import com.team8.tsuinskips.data.datasource.serializers.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class RequestCreateModelDTO(
    @Serializable(with = LocalDateSerializer::class)
    @SerializedName("startDate") val startDate: LocalDate,
    @Serializable(with = LocalDateSerializer::class)
    @SerializedName("endDate") val endDate: LocalDate,
    @SerializedName("type") val missRequestType: MissRequestType,
    @SerializedName("confirmationFiles") val confirmationFiles: List<AttachmentDto>
)