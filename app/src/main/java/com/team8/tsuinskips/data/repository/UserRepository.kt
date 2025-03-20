package com.team8.tsuinskips.data.repository

import android.util.Log
import androidx.core.content.edit
import com.team8.tsuinskips.common.application.InSkipsApplication
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
        try {
            val resp = RetrofitApi.Auth.register(UserRegisterMapper.map(userRegister))
            val token = resp.body()
            Log.i("Register", token!!.token)
            InSkipsApplication.getApp().appSharedPref.edit {
                putString("token", token.token)
                putString("email", userRegister.email)
                putString("passwd", userRegister.password)
                apply()
            }
            return TokenMapper.map(token!!)
        } catch (ex: Exception) {

        }
        return Token("")
    }

    override suspend fun login(userLogin: UserLogin): Token {
        try {
            val resp = RetrofitApi.Auth.login(UserLoginMapper.map(userLogin))
            val token = resp.body()
            Log.i("Login", token!!.token)
            InSkipsApplication.getApp().appSharedPref.edit {
                putString("token", token.token)
                putString("email", userLogin.email)
                putString("passwd", userLogin.password)
                apply()
            }
            return TokenMapper.map(token!!)
        } catch (ex: Exception) {
            Log.e("SERIALIZER ERROR???", "")
        }
        return Token("")
    }

    override suspend fun logout() {
        try {
            val resp = RetrofitApi.Auth.logout()
            Log.i("LOGOUT", resp.body().toString())
            InSkipsApplication.getApp().appSharedPref.edit {
                putString("token", "")
                putString("email", "")
                putString("passwd", "")
                apply()
            }
        } catch (ex: Exception) {

        }
    }

    override suspend fun getProfile(): User {
        try {
            val resp = RetrofitApi.Auth.getProfile()
            val usr = UserMapper.map(resp.body()!!)
            return usr
        } catch (ex: Exception) {
            return User("", "", "", "", "", emptyList(), "")
        }
    }
}