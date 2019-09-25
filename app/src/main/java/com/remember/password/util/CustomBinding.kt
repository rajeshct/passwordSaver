package com.remember.password.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("drawableRes")
fun setDrawable(imageView: ImageView, drawable: Drawable) {
    imageView.setImageDrawable(drawable)
}

@BindingAdapter("updateAdapter")
fun updateAdapter(recyclerView: RecyclerView, isUpdateAdapter: Boolean) {
    if (isUpdateAdapter && recyclerView.adapter != null) {
        recyclerView.adapter?.notifyDataSetChanged()
    }
}