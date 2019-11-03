package com.remember.password.view.masterpassword.viewmodel

import android.app.Application
import android.os.Bundle
import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.remember.password.BR
import com.remember.password.R
import com.remember.password.base.BaseViewModel
import com.remember.password.repository.Repository
import com.remember.password.util.*
import com.remember.password.view.masterpassword.MasterPasswordFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MasterPasswordViewModel(
    private val customApplication: Application
    , private val repository: Repository
) : BaseViewModel(customApplication) {

    private var isButtonClicked = false

    @Bindable
    var password = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.password)
            error = isButtonClicked && isAnyError(password, confirmPassword)
        }

    @Bindable
    var confirmPassword = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.confirmPassword)
            error = isButtonClicked && isAnyError(password, confirmPassword)
        }

    @Bindable
    var error = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.error)
        }


    override fun setArguments(bundle: Bundle?) {
        // No action required here
    }

    fun onSubmitClick() {
        if (isPerformClick()) {
            isButtonClicked = true
            error = isAnyError(password, confirmPassword)
            if (!error) {
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        repository.saveMasterPassword(password)
                    }
                    switchScreen(
                        CustomNavigation(
                            MasterPasswordFragmentDirections.actionMasterPasswordFragmentToHomeFragment(),
                            OPEN_NEW_SCREEN
                        )
                    )
                }
            }
        }
    }

    fun getConfirmationMessage(): String {
        return if (isValidPassword(confirmPassword)) {
            if (isPasswordMatched(password, confirmPassword)) "" else
                customApplication.getString(R.string.error_pwd_match)
        } else {
            customApplication.getString(R.string.error_weak_pwd)
        }
    }
}