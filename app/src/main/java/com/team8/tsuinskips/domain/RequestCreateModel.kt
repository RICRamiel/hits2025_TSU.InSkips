package com.team8.tsuinskips.domain

import com.team8.tsuinskips.data.datasource.MissRequestType
import java.time.LocalDate

data class RequestCreateModel(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val missRequestType: MissRequestType,
    val confirmationFiles: List<Attachment>
)