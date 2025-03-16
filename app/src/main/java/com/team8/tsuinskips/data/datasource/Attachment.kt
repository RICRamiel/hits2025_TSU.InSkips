package com.team8.tsuinskips.data.datasource

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Attachment(
    @SerializedName("fileName") val fileName: String, @SerializedName("file") val file: String
)
