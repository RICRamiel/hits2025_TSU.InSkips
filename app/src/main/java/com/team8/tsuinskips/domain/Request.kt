package com.team8.tsuinskips.domain

import com.team8.tsuinskips.data.datasource.Status
import com.team8.tsuinskips.data.datasource.StudentDTO
import com.team8.tsuinskips.data.datasource.MissRequestType

data class Request(
    val id: String,
    val startDate: String,
    val endDate: String,
    val creator: StudentDTO,
    val missRequestType: MissRequestType,
    val status: Status,
    val confirmationFiles: List<ConfirmationFile>
)