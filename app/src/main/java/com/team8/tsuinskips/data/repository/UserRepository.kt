package com.team8.tsuinskips.data.repository

import android.util.Log
import com.team8.tsuinskips.data.RetrofitApi
import com.team8.tsuinskips.data.mapper.TokenMapper
import com.team8.tsuinskips.data.mapper.UserLoginMapper
import com.team8.tsuinskips.data.mapper.UserRegisterMapper
import com.team8.tsuinskips.domain.Token
import com.team8.tsuinskips.domain.UserInterface
import com.team8.tsuinskips.domain.UserLogin
import com.team8.tsuinskips.domain.UserRegister

object UserRepository : UserInterface {
    override suspend fun register(userRegister: UserRegister): Token {
        val resp = RetrofitApi.Auth.register(UserRegisterMapper.map(userRegister))
        try {
            val token = resp.body()
            Log.i("Register", token!!.token)
            return TokenMapper.map(token!!)
        } catch (ex: Exception) {

        }
        return Token("")
    }

    override suspend fun login(userLogin: UserLogin): Token {
        val resp = RetrofitApi.Auth.login(UserLoginMapper.map(userLogin))
        try {
            val token = resp.body()
            Log.i("Login", token!!.token)
            return TokenMapper.map(token!!)
        } catch (ex: Exception) {

        }
        return Token("")
    }

    override suspend fun logout() {
        try {
            val resp = RetrofitApi.Auth.logout()
        } catch (ex: Exception) {

        }
    }
}