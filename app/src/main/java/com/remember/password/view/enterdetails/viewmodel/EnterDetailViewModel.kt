package com.remember.password.view.enterdetails.viewmodel

import android.app.Application
import android.os.Bundle
import android.text.TextUtils
import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.remember.password.BR
import com.remember.password.base.BaseViewModel
import com.remember.password.database.entity.RecordEntity
import com.remember.password.repository.Repository
import com.remember.password.util.CLOSE_DIALOG
import com.remember.password.util.isPerformClick
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EnterDetailViewModel(application: Application, private val repository: Repository) :
    BaseViewModel(application) {

    @Bindable
    val recordEntity = RecordEntity()

    @Bindable
    var showError = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.showError)
        }

    override fun setArguments(bundle: Bundle?) {

    }

    fun onSubmitClick() {
        if (isPerformClick()) {
            if (TextUtils.isEmpty(recordEntity.title) ||
                TextUtils.isEmpty(recordEntity.password)
                || TextUtils.isEmpty(recordEntity.userName)
            ) {
                showError = true
            } else {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.insertRecordEntity(recordEntity)
                    withContext(coroutineContext) {
                        triggerEvent(CLOSE_DIALOG)
                    }
                }
            }
        }
    }

    fun onCancelClick() {
        triggerEvent(CLOSE_DIALOG)
    }
}