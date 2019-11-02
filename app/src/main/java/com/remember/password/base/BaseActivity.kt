package com.remember.password.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.remember.password.BR
import com.remember.password.util.CustomNavigation
import com.remember.password.util.OPEN_NEW_SCREEN

abstract class BaseActivity<VB : ViewDataBinding?, VM : BaseViewModel?> : AppCompatActivity(),
    IViewRequired<VM> {

    protected var binding: VB? = null

    abstract fun getNavHost(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        setThemeOfUserChoice()
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<VB>(this, getViewToInflate())
        binding?.setVariable(BR.viewModel, getViewModel())
        intent.extras?.let { getViewModel()?.setArguments(it) }
        binding?.executePendingBindings()
        actionAfterViewInflated()
        observeChanges()
    }

    private fun observeChanges() {
        getViewModel()?.getSwitchScreen()?.observe(this, Observer {
            if (it == null) {
                return@Observer
            }
            uiScreenNavigation(it)
            getViewModel()?.switchScreen(null)
        })
    }

    fun uiScreenNavigation(navigation: CustomNavigation) {
        when (navigation.actionId) {
            OPEN_NEW_SCREEN -> {
                navigation.navigation?.let { direction ->
                    findNavController(getNavHost()).navigate(direction)
                }
            }
        }
    }

    protected open fun setThemeOfUserChoice() {}

}