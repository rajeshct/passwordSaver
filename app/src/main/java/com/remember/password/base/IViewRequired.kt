package com.remember.password.base

import androidx.lifecycle.AndroidViewModel

interface IViewRequired<VM : AndroidViewModel?> {
    abstract fun getViewToInflate(): Int
    abstract fun getViewModel(): VM?
    abstract fun actionAfterViewInflated()
}