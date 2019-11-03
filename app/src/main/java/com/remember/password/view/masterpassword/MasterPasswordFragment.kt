package com.remember.password.view.masterpassword

import com.remember.password.R
import com.remember.password.base.BaseFragment
import com.remember.password.databinding.FragmentMasterPasswordBinding
import com.remember.password.view.landing.viewmodel.HomeScreenViewModel
import com.remember.password.view.masterpassword.viewmodel.MasterPasswordViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MasterPasswordFragment : BaseFragment<FragmentMasterPasswordBinding
        , MasterPasswordViewModel>() {

    private val masterPasswordViewModel: MasterPasswordViewModel by viewModel()
    private val homeScreenViewModel: HomeScreenViewModel by sharedViewModel()

    override fun getViewToInflate(): Int {
        return R.layout.fragment_master_password
    }

    override fun getViewModel(): MasterPasswordViewModel? {
        return masterPasswordViewModel
    }

    override fun actionAfterViewInflated() {
        homeScreenViewModel.hideSearch = true
        homeScreenViewModel.hideSwitchTheme = true
        homeScreenViewModel.hideEmptyView = true
    }
}