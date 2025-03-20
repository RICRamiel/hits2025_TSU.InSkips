package com.team8.tsuinskips.data.datasource

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RequestPagedListDTO(
    val paginationDto: PaginationDto,
    val requests: List<RequestDTO>
)
