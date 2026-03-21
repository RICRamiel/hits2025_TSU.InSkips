package com.team8.tsuinskips.data.datasource

import kotlinx.serialization.Serializable

@Serializable
data class RequestPagedListDTO(
    val paginationDto: PaginationDto,
    val requests: List<RequestDTO>
)
