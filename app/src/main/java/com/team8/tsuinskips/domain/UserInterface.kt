package com.team8.tsuinskips.domain

interface UserInterface {
    suspend fun register(userRegister: UserRegister): Token
    suspend fun login(userLogin: UserLogin): Token
    suspend fun logout()
}