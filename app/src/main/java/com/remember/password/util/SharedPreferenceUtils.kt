package com.remember.password.util

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceUtils(context: Context) {

    var pref: SharedPreferences =
        context.getSharedPreferences("secure_file", Context.MODE_PRIVATE)

    fun setBoolean(key: String, value: Boolean) {
        pref.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String): Boolean {
        return pref.getBoolean(key, false)
    }
}