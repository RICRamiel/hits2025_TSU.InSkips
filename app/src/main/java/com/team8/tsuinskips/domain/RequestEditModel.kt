package com.team8.tsuinskips.domain

import com.team8.tsuinskips.data.datasource.Type

data class RequestEditModel(
    val startDate: String, val endDate: String, val type: Type, val status: String
)
