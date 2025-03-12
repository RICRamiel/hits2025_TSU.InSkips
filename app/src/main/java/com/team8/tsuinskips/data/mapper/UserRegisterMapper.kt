package com.team8.tsuinskips.data.mapper

import com.team8.tsuinskips.data.datasource.UserRegisterDTO
import com.team8.tsuinskips.domain.UserRegister

object UserRegisterMapper {
    fun map(dto: UserRegisterDTO): UserRegister {
        return with(dto) {
            UserRegister(
                username, email, surname, patronymic, password
            )
        }
    }

    fun map(dto: UserRegister): UserRegisterDTO {
        return with(dto) {
            UserRegisterDTO(
                username, email, surname, patronymic, password
            )
        }
    }
}