package com.remember.password.base

import androidx.lifecycle.AndroidViewModel

interface IViewRequired<VM : AndroidViewModel?> {
    fun getViewToInflate(): Int
    fun getViewModel(): VM?
    fun actionAfterViewInflated()
}