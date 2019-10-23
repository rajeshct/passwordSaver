package com.remember.password.data

data class UiRecord(
    var id: Int,
    var title: String,
    var userName: String,
    var pwd: String,
    var isHeader: Boolean = false,
    var showPassword: Boolean = false
)