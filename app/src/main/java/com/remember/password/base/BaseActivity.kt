package com.remember.password.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.remember.password.BR

abstract class BaseActivity<VB : ViewDataBinding?, VM : BaseViewModel?> : AppCompatActivity(),
    IViewRequired<VM> {

    private var binding: VB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<VB>(this, getViewToInflate())
        binding?.setVariable(BR.viewModel, getViewModel())
    }

}