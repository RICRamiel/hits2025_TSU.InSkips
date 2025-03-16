package com.team8.tsuinskips.domain

import com.team8.tsuinskips.data.datasource.Attachment
import com.team8.tsuinskips.data.datasource.Type

data class RequestCreateModel(
    val startDate: String,
    val endDate: String,
    val type: Type,
    val confirmationFiles: List<Attachment>
)