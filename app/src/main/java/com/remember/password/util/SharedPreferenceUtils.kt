package com.remember.password.util

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceUtils(val application: Application) {

    var pref: SharedPreferences =
        application.getSharedPreferences("secure_file", Context.MODE_PRIVATE)

    fun setBoolean(key: String, value: Boolean) {
        pref.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String): Boolean {
        return pref.getBoolean(key, false)
    }
}