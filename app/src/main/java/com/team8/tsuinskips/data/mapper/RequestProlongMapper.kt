package com.team8.tsuinskips.data.mapper

import com.team8.tsuinskips.data.datasource.RequestProlongDTO
import com.team8.tsuinskips.domain.RequestProlong

object RequestProlongMapper {
    fun map(dto: RequestProlong): RequestProlongDTO {
        return with(dto) {
            RequestProlongDTO(newEndDate)
        }
    }
}