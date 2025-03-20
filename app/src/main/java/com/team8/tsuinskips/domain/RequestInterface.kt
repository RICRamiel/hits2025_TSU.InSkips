package com.team8.tsuinskips.domain

import java.time.LocalDate

interface RequestInterface {
    suspend fun prolongRequest(id: String, requestProlong: RequestProlong): String
    suspend fun createRequest(requestCreateModel: RequestCreateModel): Boolean
    suspend fun getRequests(): RequestList
    suspend fun getRequestsFiltered(
        group: String?,
        subgroups: List<String>?,
        surname: String?,
        startDate: LocalDate?,
        endDate: LocalDate?
    ): RequestList
}