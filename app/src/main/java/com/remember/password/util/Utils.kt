package com.remember.password.util

private var lastClickedTime: Long = 0

fun isPerformClick(): Boolean {
    val newTime = System.currentTimeMillis()
    val isPerformClick = newTime > (lastClickedTime + 800)
    lastClickedTime = newTime
    return isPerformClick
}

fun isValidPassword(password: String?): Boolean {
    return !(password.isNullOrBlank() || password.length < 6)
}

fun isPasswordMatched(oldPassword: String, newPassword: String): Boolean {
    return oldPassword.contentEquals(newPassword)
}

fun isAnyError(password: String, confirmPassword: String): Boolean {
    return !(isValidPassword(password)
            && isValidPassword(confirmPassword)
            && isPasswordMatched(password, confirmPassword))
}

fun isBlank(input: String?): Boolean {
    return input.isNullOrBlank()
}