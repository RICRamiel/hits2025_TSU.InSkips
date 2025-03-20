package com.team8.tsuinskips.data.datasource

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RequestDTO(
    val id: String,
    val startDate: String,
    val endDate: String,
    val creator: StudentDTO,
    val type: MissRequestType,
    val status: Status,
    val confirmationFiles: List<ConfirmationFileDTO>
)
