package com.remember.password.view.home

import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.remember.password.R
import com.remember.password.base.BaseFragment
import com.remember.password.databinding.FragmentHomeBinding
import com.remember.password.util.INVALID_ACTION
import com.remember.password.util.REFRESH_PASSWORD_LISTING
import com.remember.password.util.SHOW_SNACK_BAR_WITH_ACTION
import com.remember.password.view.home.viewmodel.HomeViewModel
import com.remember.password.view.landing.viewmodel.HomeScreenViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    private val homeViewModel: HomeViewModel by viewModel()
    private val homeScreenViewModel: HomeScreenViewModel by sharedViewModel()

    override fun actionAfterViewInflated() {
        observeListener()
        homeScreenViewModel.hideSearch = false
        homeScreenViewModel.hideSwitchTheme = false
    }

    private fun observeListener() {
        homeScreenViewModel.refreshListing.observe(requireActivity(), Observer {
            if (it == REFRESH_PASSWORD_LISTING) {
                homeViewModel.refreshListing()
                homeScreenViewModel.clearRefreshListing()
            }
        })

        homeScreenViewModel.searchText.observe(requireActivity(), Observer {
            if (it != null) {
                homeViewModel.searchUserInput(it)
                homeScreenViewModel.clearSearchText()
            }
        })

        homeViewModel.uiListingRecordFromDb.observe(viewLifecycleOwner, Observer {
            homeViewModel.updateData(it)
        })

        homeViewModel.triggerEvent.observe(viewLifecycleOwner, Observer { actionId ->
            if (actionId == SHOW_SNACK_BAR_WITH_ACTION) {
                Snackbar.make(
                    binding.parentLayout,
                    getString(R.string.alert_undo),
                    Snackbar.LENGTH_LONG
                ).setAction(getString(R.string.alert_undo_action)) {
                    homeViewModel.undoDeleteAction()
                }.show()
                homeViewModel.triggerEvent(INVALID_ACTION)
            }
        })

    }

    override fun getViewToInflate(): Int {
        return R.layout.fragment_home
    }

    override fun getViewModel(): HomeViewModel {
        return homeViewModel
    }
}