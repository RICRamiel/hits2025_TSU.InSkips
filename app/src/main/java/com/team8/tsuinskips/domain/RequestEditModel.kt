package com.team8.tsuinskips.domain

import com.team8.tsuinskips.data.datasource.MissRequestType

data class RequestEditModel(
    val startDate: String, val endDate: String, val missRequestType: MissRequestType, val status: String
)
