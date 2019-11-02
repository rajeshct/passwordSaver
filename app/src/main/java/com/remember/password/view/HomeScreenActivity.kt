package com.remember.password.view

import androidx.appcompat.widget.SearchView
import com.remember.password.R
import com.remember.password.base.BaseActivity
import com.remember.password.databinding.ActivityMainBinding
import com.remember.password.util.DARK_THEME_SET
import com.remember.password.util.SharedPreferenceUtils
import com.remember.password.view.landing.viewmodel.HomeScreenViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeScreenActivity : BaseActivity<ActivityMainBinding, HomeScreenViewModel>() {

    private val homeScreenViewModel: HomeScreenViewModel by viewModel()
    private val sharedPreferenceUtils: SharedPreferenceUtils by inject()

    override fun getViewToInflate(): Int {
        return R.layout.activity_main
    }

    override fun getViewModel(): HomeScreenViewModel? {
        return homeScreenViewModel
    }

    override fun actionAfterViewInflated() {
        setSupportActionBar(toolbar)
        binding?.swTheme?.isChecked = sharedPreferenceUtils.getBoolean(DARK_THEME_SET)
        homeScreenViewModel.isMasterPasswordSet()
        binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                homeScreenViewModel.searchText.value = query
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                homeScreenViewModel.searchText.value = newText
                return false
            }
        })
        binding?.searchView?.addOnLayoutChangeListener { v, _, _, _, _, _, _, _, _
            ->
            homeScreenViewModel.hideTitle = if (v is SearchView) {
                !v.isIconified
            } else {
                false
            }
        }
        binding?.swTheme?.setOnCheckedChangeListener { _, isChecked ->
            run {
                val lastValue = sharedPreferenceUtils.getBoolean(DARK_THEME_SET)
                if (lastValue != isChecked) {
                    sharedPreferenceUtils.setBoolean(DARK_THEME_SET, isChecked)
                    setTheme(if (isChecked) R.style.AppThemeDark else R.style.AppThemeLight)
                    recreate()
                }
            }
        }
    }

    override fun getNavHost() = R.id.nav_fragment

    override fun setThemeOfUserChoice() {
        setTheme(
            if (sharedPreferenceUtils.getBoolean(DARK_THEME_SET))
                R.style.AppThemeDark
            else R.style.AppThemeLight
        )
    }
}
