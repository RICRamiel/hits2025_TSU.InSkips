package com.team8.tsuinskips.domain

interface RequestInterface {
    suspend fun prolongRequest(id: String, requestProlong: RequestProlong): String
    suspend fun createRequest(requestCreateModel: RequestCreateModel): String
    suspend fun getRequests(): RequestList
}