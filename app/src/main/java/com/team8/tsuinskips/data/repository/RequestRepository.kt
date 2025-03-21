package com.team8.tsuinskips.data.repository

import android.graphics.Bitmap
import android.util.Log
import com.team8.tsuinskips.data.RetrofitApi
import com.team8.tsuinskips.data.datasource.AttachmentDto
import com.team8.tsuinskips.data.datasource.RequestCreateModelDTO
import com.team8.tsuinskips.data.datasource.RequestProlongDTO
import com.team8.tsuinskips.data.mapper.RequestPagedListMapper
import com.team8.tsuinskips.data.mapper.RequestProlongMapper
import com.team8.tsuinskips.domain.RequestCreateModel
import com.team8.tsuinskips.domain.RequestInterface
import com.team8.tsuinskips.domain.RequestList
import com.team8.tsuinskips.domain.RequestProlong
import retrofit2.http.Query
import java.io.ByteArrayOutputStream
import java.time.LocalDate

object RequestRepository : RequestInterface {
    override suspend fun prolongRequest(id: String, requestProlong: RequestProlong): String {
        val resp = RetrofitApi.Requests.prolongRequest(id, RequestProlongMapper.map(requestProlong))
        return try {
            resp.body().toString()
        } catch (ex: Exception) {
            ""
        }
    }

    override suspend fun createRequest(requestCreateModel: RequestCreateModel): Boolean {
        RetrofitApi.Requests.createRequest(
            RequestCreateModelDTO(
                startDate = requestCreateModel.startDate,
                endDate = requestCreateModel.endDate,
                type = requestCreateModel.missRequestType,
                confirmationFiles = requestCreateModel.confirmationFiles.map {
                    AttachmentDto(it.fileName, bitmapToByteArray(it.file))
                }
            )
        )
        return true
    }

    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

    override suspend fun getRequests(): RequestList {
        val resp = RetrofitApi.Requests.getRequests()
        try {
            val temp = RequestPagedListMapper.map(resp.body()!!)
            return temp
        } catch (ex: Exception) {
            Log.e("GETRR", ex.toString())
            return RequestList(emptyList())
        }
    }

    override suspend fun getRequestsFiltered(
        group: String?,
        subgroups: List<String>?,
        surname: String?,
        startDate: LocalDate?,
        endDate: LocalDate?
    ): RequestList {
        val resp = RetrofitApi.Requests.getRequestsFiltered(
            group,
            subgroups,
            surname,
            startDate,
            endDate
        )
        try {
            val temp = RequestPagedListMapper.map(resp.body()!!)
            return temp
        } catch (ex: Exception) {
            Log.e("GETRR", ex.toString())
            return RequestList(emptyList())
        }
    }
}