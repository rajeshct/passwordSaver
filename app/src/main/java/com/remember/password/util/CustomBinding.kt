package com.remember.password.util

import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


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

@BindingAdapter("isError", "message")
fun textInputError(textInputLayout: TextInputLayout, isError: Boolean, message: String) {
    if (isError) {
        if (TextUtils.isEmpty(message)) {
            textInputLayout.error = null
        } else {
            textInputLayout.error = message
        }
    } else {
        textInputLayout.error = null
    }
}

@BindingAdapter("font")
fun setFontInPasswordField(password: TextInputEditText, font: String) {
    val context = password.context
    val packageName = context.packageName
    val resId = context.resources.getIdentifier(font, "font", packageName)
    password.typeface = ResourcesCompat.getFont(context, resId)
}
