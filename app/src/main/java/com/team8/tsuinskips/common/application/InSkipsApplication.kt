package com.team8.tsuinskips.common.application

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.team8.tsuinskips.MainActivity

class InSkipsApplication : Application(){
    val APP_SHARED_PREF_NAME = "app_shared_pref"
    lateinit var appSharedPref: SharedPreferences
        private set

    companion object {
        private lateinit var app: InSkipsApplication

        fun getApp(): InSkipsApplication {
            return app
        }
    }

    override fun onCreate() {
        super.onCreate()
        appSharedPref = getSharedPreferences(APP_SHARED_PREF_NAME, Context.MODE_PRIVATE)
        app = this
    }

    fun restartApp(){
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        this.startActivity(intent)
    }
}