package com.team8.tsuinskips.data.repository

import android.util.Log
import com.team8.tsuinskips.data.RetrofitApi
import com.team8.tsuinskips.data.mapper.TokenMapper
import com.team8.tsuinskips.data.mapper.UserLoginMapper
import com.team8.tsuinskips.data.mapper.UserMapper
import com.team8.tsuinskips.data.mapper.UserRegisterMapper
import com.team8.tsuinskips.domain.Token
import com.team8.tsuinskips.domain.User
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
        val resp = RetrofitApi.Auth.logout()
        try {

        } catch (ex: Exception) {

        }
    }

    override suspend fun getProfile(): User {
        val resp = RetrofitApi.Auth.getProfile()
        try {
            Log.i("RepoUSERRR", "${resp.body()!!.id} ${resp.body()!!.email} ${resp.body()!!.name} ")
            val usr = UserMapper.map(resp.body()!!)
            Log.i("RepoUSER", "${usr.id} ${usr.email} ${usr.name} ")
            return usr
        } catch (ex: Exception) {
            return User("", "", "", "", "", emptyList())
        }
    }
}