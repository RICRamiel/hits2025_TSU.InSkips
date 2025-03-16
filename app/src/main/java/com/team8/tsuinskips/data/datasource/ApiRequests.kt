package com.team8.tsuinskips.data.datasource

import retrofit2.http.PUT

interface ApiRequests {
    @PUT("requests/{id}/prolong")
    fun prolongRequest()
}