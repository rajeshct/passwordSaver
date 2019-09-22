package com.remember.password.view.home

import com.remember.password.R
import com.remember.password.base.BaseFragment
import com.remember.password.databinding.FragmentHomeBinding
import com.remember.password.view.home.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    private val homeViewModel: HomeViewModel by viewModel()

    override fun actionAfterViewInflated() {

    }

    override fun getViewToInflate(): Int {
        return R.layout.fragment_home
    }

    override fun getViewModel(): HomeViewModel {
        return homeViewModel
    }
}