package com.remember.password.view.home.viewmodel

import android.app.Application
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.remember.password.BR
import com.remember.password.R
import com.remember.password.base.BaseDiAdapter
import com.remember.password.base.BaseViewModel
import com.remember.password.data.UiRecord
import com.remember.password.repository.Repository
import com.remember.password.util.SHOW_ALERT_ENTER_PASSWORD
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
    private val repository: Repository,
    private val lifecycleOwner: LifecycleOwner
) : BaseViewModel(customApplication), BaseDiAdapter.IClickCallback<UiRecord> {

    val uiListingData = mutableListOf<UiRecord>()
    var data: UiRecord? = null

    @Bindable
    var updateAdapter: Boolean = false
        private set(value) {
            field = value
            notifyPropertyChanged(BR.updateAdapter)
        }

    override fun onClick(position: Int, tag: Int, data: UiRecord?, calledFor: Int) {
        this.data = data
        triggerEvent(SHOW_ALERT_ENTER_PASSWORD)
    }

    fun actionAfterPasswordFilled() {
        if (data?.isHeader == true) {
            uiListingData.map {
                it.showPassword = !it.showPassword
            }
        } else {
            data?.showPassword = !(data?.showPassword ?: false)
        }
        updateAdapter = true
    }

    fun addNewRecord(view: View) {
        view.findNavController().navigate(R.id.action_homeFragment_to_dialogEnterDetails)
    }

    fun getUiData() {
        Transformations.map(repository.getListingRecord()) {
            val allRecords = mutableListOf<UiRecord>()
            allRecords.add(
                UiRecord(
                    title = customApplication.getString(R.string.label_title),
                    userName = customApplication.getString(R.string.label_user_name),
                    pwd = customApplication.getString(R.string.label_password)
                    , isHeader = true
                )
            )
            for (item in it) {
                allRecords.add(
                    UiRecord(
                        userName = item.userName,
                        title = item.title,
                        pwd = item.password
                    )
                )
            }
            return@map allRecords
        }.observe(lifecycleOwner, Observer {
            uiListingData.clear()
            uiListingData.addAll(it)
            updateAdapter = true
        })
    }

}