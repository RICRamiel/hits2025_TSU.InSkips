package com.team8.tsuinskips.data.datasource

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDate

interface ApiRequests {
    @PUT("requests/{id}/prolong")
    suspend fun prolongRequest(
        @Path("id") id: String, @Body prolongDTO: RequestProlongDTO
    ): Response<String>

    @POST("requests")
    suspend fun createRequest(@Body createModelDTO: RequestCreateModelDTO): Response<String>

    @GET("requests/my")
    suspend fun getRequests(): Response<RequestPagedListDTO>

    @GET("requests")
    suspend fun getRequestsFiltered(
        @Query("group") group: String?,
        @Query("subgroups") subgroups: List<String>?,
        @Query("studentSurname") surname: String?,
        @Query("startDate") startDate: LocalDate?,
        @Query("endDate") endDate: LocalDate?
    ): Response<RequestPagedListDTO>
}