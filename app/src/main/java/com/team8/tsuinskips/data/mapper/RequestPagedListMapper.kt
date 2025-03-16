package com.team8.tsuinskips.data.mapper

import android.util.Log
import com.team8.tsuinskips.data.datasource.RequestPagedListDTO
import com.team8.tsuinskips.domain.RequestList
import com.team8.tsuinskips.data.mapper.RequestMapper

object RequestPagedListMapper {
    fun map(dto: RequestPagedListDTO): RequestList {
        return with(dto) {
            RequestList(requests = requests.map { RequestMapper.map(it) })
        }
    }
}