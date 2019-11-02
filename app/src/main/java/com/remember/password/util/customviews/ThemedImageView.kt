package com.remember.password.util.customviews

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.remember.password.util.DARK_THEME_SET
import com.remember.password.util.SharedPreferenceUtils

class ThemedImageView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    AppCompatImageView(context, attrs, defStyleAttr) {

    init {
        val sharedPreferenceUtils = context?.let { SharedPreferenceUtils(it) }
        setColorFilter(
            if (sharedPreferenceUtils?.getBoolean(DARK_THEME_SET) == true) Color.parseColor(
                "#F0F0F0"
            ) else Color.parseColor("#272626")
        )
    }

    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)
}