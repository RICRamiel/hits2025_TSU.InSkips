package com.team8.tsuinskips.data.mapper

import com.team8.tsuinskips.data.datasource.UserDTO
import com.team8.tsuinskips.domain.User

object UserMapper {
    fun map(dto: UserDTO): User {
        return with(dto) {
            User(id, name, email, surname, patronymic, roles, groupName)
        }
    }
}