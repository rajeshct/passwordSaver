package com.remember.password.view

import com.remember.password.R
import com.remember.password.base.BaseActivity
import com.remember.password.databinding.ActivityMainBinding
import com.remember.password.view.landing.viewmodel.LandingViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LandingActivity : BaseActivity<ActivityMainBinding, LandingViewModel>() {

    private val landingViewModel: LandingViewModel by viewModel()

    override fun getViewToInflate(): Int {
        return R.layout.activity_main
    }

    override fun getViewModel(): LandingViewModel? {
        return landingViewModel
    }

    override fun actionAfterViewInflated() {
        setSupportActionBar(toolbar)
        landingViewModel.isMasterPasswordSet()
    }

    override fun getNavHost() = R.id.nav_fragment

}
