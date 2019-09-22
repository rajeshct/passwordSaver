package com.remember.password

import com.remember.password.base.BaseActivity
import com.remember.password.base.BaseViewModel
import com.remember.password.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, BaseViewModel>() {

    override fun getViewToInflate(): Int {
        return R.layout.activity_main
    }

    override fun getViewModel(): BaseViewModel? {
        return null
    }

    override fun actionAfterViewInflated() {

    }
}
