package com.team8.tsuinskips.data.mapper

import android.util.Log
import com.team8.tsuinskips.data.datasource.RequestDTO
import com.team8.tsuinskips.domain.Request

object RequestMapper {
    fun map(dto: RequestDTO): Request {
        return with(dto) {
            Log.i("REQUESTMAPPP", id)
            Request(id,
                startDate,
                endDate,
                creator,
                missRequestType,
                status,
                confirmationFiles.map { ConfirmationFileMapper.map(it) })

        }
    }

    fun map(dto: Request): RequestDTO {
        return with(dto) {
            RequestDTO(id,
                startDate,
                endDate,
                creator,
                missRequestType,
                status,
                confirmationFiles.map { ConfirmationFileMapper.map(it) })
        }
    }
}