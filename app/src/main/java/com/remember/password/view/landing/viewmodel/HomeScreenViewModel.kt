package com.remember.password.view.landing.viewmodel

import android.app.Application
import android.os.Bundle
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.remember.password.BR
import com.remember.password.base.BaseViewModel
import com.remember.password.repository.Repository
import com.remember.password.util.CustomNavigation
import com.remember.password.util.INVALID_ACTION
import com.remember.password.util.OPEN_NEW_SCREEN
import com.remember.password.view.home.HomeFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeScreenViewModel(application: Application, private val repository: Repository) :
    BaseViewModel(application) {

    val refreshListing = MutableLiveData<Int>()
    val searchText = MutableLiveData<String>()

    @Bindable
    var hideSearch: Boolean = false
        internal set(value) {
            field = value
            notifyPropertyChanged(BR.hideSearch)
        }

    @Bindable
    var hideSwitchTheme: Boolean = false
        internal set(value) {
            field = value
            notifyPropertyChanged(BR.hideSwitchTheme)
        }

    @Bindable
    var hideTitle: Boolean = false
        internal set(value) {
            field = value
            notifyPropertyChanged(BR.hideTitle)
        }

    @Bindable
    var hideEmptyView = false
        internal set(value) {
            field = value
            notifyPropertyChanged(BR.hideEmptyView)
        }


    override fun setArguments(bundle: Bundle?) {
        // No action required here
    }

    fun isMasterPasswordSet() {
        viewModelScope.launch {
            val isPasswordSaved = withContext(Dispatchers.IO) {
                repository.isMasterPasswordSet()
            }
            if (!isPasswordSaved) {
                switchScreen(
                    CustomNavigation(
                        HomeFragmentDirections.actionHomeFragmentToMasterPasswordFragment(),
                        OPEN_NEW_SCREEN
                    )
                )
            } else {
                hideEmptyView = true
            }
        }
    }

    fun clearRefreshListing() {
        refreshListing.value = INVALID_ACTION
    }

    fun clearSearchText() {
        searchText.value = null
    }
}