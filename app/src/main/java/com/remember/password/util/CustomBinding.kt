package com.remember.password.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("drawableRes")
fun setDrawable(imageView: ImageView, drawable: Drawable) {
    imageView.setImageDrawable(drawable)
}