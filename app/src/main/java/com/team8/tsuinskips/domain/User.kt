package com.team8.tsuinskips.domain

import com.team8.tsuinskips.data.datasource.Roles

data class User(
    val id: String,
    val name: String,
    val email: String,
    val surname: String,
    val patronymic: String,
    val roles: List<Roles>
)
