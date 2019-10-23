package com.remember.password.util

import androidx.navigation.NavDirections

data class CustomNavigation(
    var navigation: NavDirections? = null,
    var actionId: Int,
    var data: Any? = null
)