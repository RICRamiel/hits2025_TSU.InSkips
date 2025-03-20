package com.team8.tsuinskips.data.datasource

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class PaginationDto(
    val page: Int,
    val pageSize: Int,
    val count: Int,
)