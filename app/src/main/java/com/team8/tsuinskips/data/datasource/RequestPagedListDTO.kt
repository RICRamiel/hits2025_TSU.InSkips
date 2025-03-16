package com.team8.tsuinskips.data.datasource

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RequestPagedListDTO(
    @SerializedName("paginationDto") val pages: PaginationDTO,
    @SerializedName("requests") val requests: List<RequestDTO>
)
