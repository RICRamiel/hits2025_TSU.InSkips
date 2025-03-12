package com.team8.tsuinskips.data.mapper

import com.team8.tsuinskips.data.datasource.UserLoginDTO
import com.team8.tsuinskips.domain.UserLogin

object UserLoginMapper {
    fun map(dto: UserLoginDTO): UserLogin {
        return with(dto) {
            UserLogin(
                email, password
            )
        }
    }

    fun map(dto: UserLogin): UserLoginDTO {
        return with(dto) {
            UserLoginDTO(
                email, password
            )
        }
    }
}