package com.team8.tsuinskips.data.datasource

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class PaginationDTO(
    @SerializedName("page") val page: Int,
    @SerializedName("pageSize") val pageSize: Int,
    @SerializedName("count") val count: Int,
)