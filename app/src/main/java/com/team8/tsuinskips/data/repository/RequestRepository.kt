package com.team8.tsuinskips.data.repository

import android.util.Log
import com.team8.tsuinskips.data.RetrofitApi
import com.team8.tsuinskips.data.mapper.RequestPagedListMapper
import com.team8.tsuinskips.data.mapper.RequestProlongMapper
import com.team8.tsuinskips.domain.Request
import com.team8.tsuinskips.domain.RequestCreateModel
import com.team8.tsuinskips.domain.RequestInterface
import com.team8.tsuinskips.domain.RequestList
import com.team8.tsuinskips.domain.RequestProlong

object RequestRepository : RequestInterface {
    override suspend fun prolongRequest(id: String, requestProlong: RequestProlong): String {
        val resp = RetrofitApi.Requests.prolongRequest(id, RequestProlongMapper.map(requestProlong))
        try {
            return resp.body().toString()
        } catch (ex: Exception) {
            return ""
        }
    }

    override suspend fun createRequest(requestCreateModel: RequestCreateModel): String {
        TODO("Not yet implemented")
    }

    override suspend fun getRequests(): RequestList {
        val resp = RetrofitApi.Requests.getRequests()
        try {
            Log.i("LIST0", resp.body()!!.requests[0].id)
            val temp = RequestPagedListMapper.map(resp.body()!!)
            Log.i("LIST1", temp.requests[0].id)

            return temp
        } catch (ex: Exception) {
            Log.e("GETRR", ex.toString())
            return RequestList(emptyList())
        }
    }
}