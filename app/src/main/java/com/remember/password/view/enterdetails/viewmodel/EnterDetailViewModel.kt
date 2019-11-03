package com.remember.password.view.enterdetails.viewmodel

import android.app.Application
import android.os.Bundle
import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.remember.password.BR
import com.remember.password.base.BaseViewModel
import com.remember.password.data.UiRecord
import com.remember.password.database.entity.RecordEntity
import com.remember.password.repository.Repository
import com.remember.password.util.*
import com.remember.password.view.enterdetails.DialogEnterDetailsArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EnterDetailViewModel(application: Application, private val repository: Repository) :
    BaseViewModel(application) {

    private var openFor = INSERT_PASSWORD
    private var previousFilledRecord: UiRecord? = null

    private var recordEntity: RecordEntity? = null
        get() {
            if (field == null) {
                field = RecordEntity()
            }
            return field
        }

    @Bindable
    var allowUpdate = false
        private set(value) {
            field = value
            notifyPropertyChanged(BR.allowUpdate)
        }

    @Bindable
    var buttonClicked = false
        private set(value) {
            field = value
            notifyPropertyChanged(BR.buttonClicked)
        }

    @Bindable
    var title: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.title)
            field?.let {
                recordEntity?.title = it
            }
        }

    @Bindable
    var userName: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.userName)
            field?.let {
                recordEntity?.userName = it
            }
        }

    @Bindable
    var password: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.password)
            field?.let {
                recordEntity?.password = it
            }
        }

    override fun setArguments(bundle: Bundle?) {
        bundle?.let {
            val safeArgs = DialogEnterDetailsArgs.fromBundle(it)
            openFor = safeArgs.openFor
            if (openFor == INSERT_PASSWORD) {
                allowUpdate = true
            }
            previousFilledRecord = safeArgs.data
            previousFilledRecord?.let { uiRecord ->
                title = uiRecord.title
                userName = uiRecord.userName
                recordEntity?.id = uiRecord.id
            }
        }
    }

    fun onSubmitClick() {
        if (isPerformClick()) {
            if (openFor == UPDATE_PASSWORD) {
                updateRecord()
            } else {
                buttonClicked = true
                insertRecord()
            }
        }
    }

    private fun insertRecord() {
        if (recordEntity?.validRecord() == true) {
            viewModelScope.launch(Dispatchers.IO) {
                recordEntity?.let {
                    repository.insertRecordEntity(it)
                }
                triggerEvent(CLOSE_DIALOG)
            }
        }
    }

    private fun updateRecord() {
        if (allowUpdate) {
            buttonClicked = true
            if (recordEntity?.validRecord() == true) {
                viewModelScope.launch(Dispatchers.IO) {
                    recordEntity?.let {
                        repository.updateRecordEntity(it)
                    }
                    triggerEvent(CLOSE_DIALOG)
                }
            }
        } else {
            if ((password?.let { repository.isMasterPasswordMatched(it) }) == true) {
                allowUpdate = true
                password = previousFilledRecord?.pwd
            } else {
                triggerEvent(SHOW_SNACK_BAR)
            }
        }
    }

    fun onCancelClick() {
        triggerEvent(CLOSE_DIALOG)
    }
}