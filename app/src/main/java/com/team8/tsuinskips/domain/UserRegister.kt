package com.team8.tsuinskips.domain

data class UserRegister(
    val username: String,
    val email: String,
    val surname: String,
    val patronymic: String,
    val password: String,
)
