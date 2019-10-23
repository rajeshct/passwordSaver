package com.remember.password.view.home

import androidx.lifecycle.Observer
import com.remember.password.R
import com.remember.password.base.BaseFragment
import com.remember.password.databinding.FragmentHomeBinding
import com.remember.password.util.REFRESH_PASSWORD_LISTING
import com.remember.password.view.home.viewmodel.HomeViewModel
import com.remember.password.view.landing.viewmodel.LandingViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    private val homeViewModel: HomeViewModel by viewModel()
    private val landingViewModel: LandingViewModel by sharedViewModel()

    override fun actionAfterViewInflated() {
        observeListener()
    }

    private fun observeListener() {
        landingViewModel.refreshListing.observe(requireActivity(), Observer {
            if (it == REFRESH_PASSWORD_LISTING) {
                homeViewModel.refreshListing()
            }
        })
        homeViewModel.getUiData().observe(this, Observer {
            homeViewModel.updateData(it)
        })
    }

    override fun getViewToInflate(): Int {
        return R.layout.fragment_home
    }

    override fun getViewModel(): HomeViewModel {
        return homeViewModel
    }
}