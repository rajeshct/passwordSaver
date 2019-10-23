package com.remember.password.view.home.viewmodel

import android.app.Application
import android.os.Bundle
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.remember.password.BR
import com.remember.password.R
import com.remember.password.base.BaseDiAdapter
import com.remember.password.base.BaseViewModel
import com.remember.password.data.UiRecord
import com.remember.password.repository.Repository
import com.remember.password.util.CustomNavigation
import com.remember.password.util.INPUT_PASSWORD
import com.remember.password.util.OPEN_NEW_SCREEN
import com.remember.password.util.isPerformClick
import com.remember.password.view.home.HomeFragmentDirections
import com.remember.password.view.home.adapter.HomeListingAdapter

@BindingAdapter("viewModel")
fun setListingAdapter(recyclerView: RecyclerView, viewModel: HomeViewModel?) {
    if (viewModel != null && recyclerView.adapter == null) {
        val adapter = HomeListingAdapter(viewModel.uiListingData)
        recyclerView.adapter = adapter
        if (recyclerView.adapter is BaseDiAdapter<*>) {
            (recyclerView.adapter as BaseDiAdapter<*>).registerClickCallBack(viewModel)
        }
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    }
}

class HomeViewModel(
    private val customApplication: Application,
    private val repository: Repository
) : BaseViewModel(customApplication), BaseDiAdapter.IClickCallback<UiRecord> {

    val uiListingData = mutableListOf<UiRecord>()
    var data: UiRecord? = null

    @Bindable
    var updateAdapter = false
        private set(value) {
            field = value
            notifyPropertyChanged(BR.updateAdapter)
        }

    @Bindable
    var noRecord = false
        private set(value) {
            field = value
            notifyPropertyChanged(BR.noRecord)
        }

    override fun setArguments(bundle: Bundle?) {
        // No action required here
    }

    override fun onClick(position: Int, tag: Int, data: UiRecord?, calledFor: Int) {
        this.data = data
        if (this.data?.showPassword == true) {
            this.data?.apply {
                showPassword = false
            }
            updateAdapter = true
        } else {
            switchScreen(
                CustomNavigation(
                    navigation = HomeFragmentDirections.actionHomeFragmentToInputPasswordFragment(
                        INPUT_PASSWORD
                    ), actionId = OPEN_NEW_SCREEN
                )
            )
        }
    }

    fun addNewRecord() {
        if (isPerformClick()) {
            switchScreen(
                CustomNavigation(
                    HomeFragmentDirections.actionHomeFragmentToDialogEnterDetails(),
                    OPEN_NEW_SCREEN
                )
            )
        }
    }

    fun getUiData(): LiveData<MutableList<UiRecord>> {
        return Transformations.map(repository.getListingRecord()) {
            val allRecords = mutableListOf<UiRecord>()
            it.forEach { recordEntity ->
                with(recordEntity) {
                    allRecords.add(
                        UiRecord(
                            id = id ?: 0,
                            userName = userName,
                            title = title,
                            pwd = password
                        )
                    )
                }
            }
            return@map allRecords
        }
    }

    fun refreshListing() {
        if (data?.isHeader == true) {
            if (data?.showPassword == true) {
                changeShowPassword(false)
            } else {
                changeShowPassword(true)
            }
        } else {
            data?.apply {
                showPassword = !showPassword
            }
        }
        updateAdapter = true
    }

    private fun changeShowPassword(showPassword: Boolean) {
        uiListingData.forEach(action = {
            it.apply {
                this.showPassword = showPassword
            }
        })
    }

    fun updateData(it: MutableList<UiRecord>?) {
        if (it == null || it.isEmpty()) {
            noRecord = true
        } else {
            uiListingData.clear()
            uiListingData.add(
                UiRecord(
                    id = 0,
                    title = customApplication.getString(R.string.label_title),
                    userName = customApplication.getString(R.string.label_user_name),
                    pwd = customApplication.getString(R.string.label_password),
                    isHeader = true,
                    showPassword = true
                )
            )
            uiListingData.addAll(it)
            updateAdapter = true
            noRecord = false
        }
    }

}