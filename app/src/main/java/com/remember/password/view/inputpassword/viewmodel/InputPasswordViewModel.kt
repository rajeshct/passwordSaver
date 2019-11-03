package com.remember.password.view.inputpassword.viewmodel

import android.app.Application
import android.os.Bundle
import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.remember.password.BR
import com.remember.password.base.BaseViewModel
import com.remember.password.repository.Repository
import com.remember.password.util.*
import com.remember.password.view.inputpassword.InputPasswordFragmentArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InputPasswordViewModel(customApplication: Application, private val repository: Repository) :
    BaseViewModel(customApplication) {

    private var isButtonClicked = false

    @Bindable
    var enableError = false
        private set(value) {
            field = value
            notifyPropertyChanged(BR.enableError)
        }

    @Bindable
    var openFor = 0
        private set(value) {
            field = value
            notifyPropertyChanged(BR.openFor)
        }

    @Bindable
    var password = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.password)
            enableError = isButtonClicked && isEnableError()
        }

    @Bindable
    var newPassword = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.newPassword)
            enableError = isButtonClicked && isEnableError()
        }

    @Bindable
    var confirmPassword = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.confirmPassword)
            enableError = isButtonClicked && isEnableError()
        }

    private fun isEnableError(): Boolean {
        return when (openFor) {
            VALIDATE_PASSWORD -> !isValidPassword(password)
            UPDATE_PASSWORD -> !isValidPassword(password)
            else -> false
        }
    }

    override fun setArguments(bundle: Bundle?) {
        bundle?.let {
            val safeArgs = InputPasswordFragmentArgs.fromBundle(it)
            openFor = safeArgs.openFor
        }
    }

    fun submitAction() {
        if (isPerformClick()) {
            isButtonClicked = true
            enableError = isEnableError()
            if (!enableError) {
                if (openFor == VALIDATE_PASSWORD) {
                    submitPasswordAction()
                } else if (openFor == UPDATE_PASSWORD) {
                    updatePasswordAction()
                }
            }
        }
    }

    private fun submitPasswordAction() {
        if (repository.isMasterPasswordMatched(password)) {
            triggerEvent(CLOSE_DIALOG_WITH_SUCCESS)
        } else {
            triggerEvent(SHOW_SNACK_BAR)
        }
    }

    private fun updatePasswordAction() {
        if (repository.isMasterPasswordMatched(password)) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    repository.saveMasterPassword(newPassword)
                }
                triggerEvent(CLOSE_DIALOG)
            }
        } else {
            triggerEvent(SHOW_SNACK_BAR)
        }
    }

    fun onCancelClick() {
        triggerEvent(CLOSE_DIALOG)
    }
}