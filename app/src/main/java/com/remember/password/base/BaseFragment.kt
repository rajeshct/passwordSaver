package com.remember.password.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import com.remember.password.BR

abstract class BaseFragment<VB, VM>
    : Fragment(),
    IViewRequired<VM> where VM : BaseViewModel, VM : LifecycleObserver, VB : ViewDataBinding {

    private lateinit var binding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getViewToInflate(), container, false)
        binding.setVariable(BR.viewModel, getViewModel())
        getViewModel()?.let { lifecycle.addObserver(it) }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionAfterViewInflated()
    }

}