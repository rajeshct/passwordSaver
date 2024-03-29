package com.remember.password.view.home.viewmodel

import android.app.Application
import android.os.Bundle
import androidx.arch.core.util.Function
import androidx.core.content.ContextCompat
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.remember.password.BR
import com.remember.password.R
import com.remember.password.base.BaseDiAdapter
import com.remember.password.base.BaseViewModel
import com.remember.password.data.UiRecord
import com.remember.password.repository.Repository
import com.remember.password.util.*
import com.remember.password.view.home.HomeFragmentDirections
import com.remember.password.view.home.adapter.HomeListingAdapter

@BindingAdapter("viewModel")
fun setListingAdapter(recyclerView: RecyclerView, viewModel: HomeViewModel?) {
    if (viewModel != null && recyclerView.adapter == null) {
        val adapter = HomeListingAdapter(viewModel.uiListingData)
        viewModel.passwordListingAdapter = adapter
        recyclerView.adapter = adapter
        if (recyclerView.adapter is BaseDiAdapter<*>) {
            (recyclerView.adapter as BaseDiAdapter<*>).registerClickCallBack(viewModel)
        }
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        ContextCompat.getDrawable(recyclerView.context, R.drawable.ic_delete_sweep)?.let {
            SwipeToDeleteCallback(
                adapter, it
            )
        }?.let {
            ItemTouchHelper(
                it
            ).attachToRecyclerView(recyclerView)
        }
    }
}

class HomeViewModel(
    private val customApplication: Application,
    private val repository: Repository
) : BaseViewModel(customApplication), BaseDiAdapter.IClickCallback {

    private var lastClickedPosition = -1
    var passwordListingAdapter: HomeListingAdapter? = null
    private val searchChange = MutableLiveData<String>()
    private var lastSavedUiRecord: UiRecord? = null
    val uiListingData = mutableListOf<UiRecord>()

    var uiListingRecordFromDb: LiveData<List<UiRecord>> =
        Transformations.switchMap(searchChange, Function {
            if (it.isNullOrBlank()) {
                return@Function repository.getPasswordRecordForUi()
            }
            return@Function repository.getRecordBasedOnUserSearch(it)
        })

    private var deletedItems: MutableList<UiRecord>? = null
        get() {
            if (field == null) {
                field = mutableListOf()
            }
            return field
        }

    @Bindable
    var errorViewType: Int = SHOW_ADD_NEW_RECORD
        private set(value) {
            field = value
            notifyPropertyChanged(BR.errorViewType)
        }

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
        getUiData()
    }

    override fun onClick(position: Int, tag: Int, data: Any?, calledFor: Int) {
        if (data is UiRecord) {
            if (calledFor == TAG_SHOW_HIDE_PASSWORD) {
                showHideIconClick(position, data)
            } else if (calledFor == UPDATE_PASSWORD) {
                updatePassword(data)
            }
        }
    }

    private fun updatePassword(data: UiRecord) {
        val direction =
            HomeFragmentDirections.actionHomeFragmentToDialogEnterDetails(UPDATE_PASSWORD)
        direction.data = data
        switchScreen(CustomNavigation(navigation = direction, actionId = OPEN_NEW_SCREEN))
    }

    private fun showHideIconClick(position: Int, data: UiRecord) {
        this.lastClickedPosition = position
        this.lastSavedUiRecord = data
        if (this.lastSavedUiRecord?.showPassword == true) {
            if (data.isHeader) {
                refreshListing()
            } else {
                this.lastSavedUiRecord?.apply {
                    showPassword = false
                }
                passwordListingAdapter?.notifyItemChanged(position)
            }
        } else {
            switchScreen(
                CustomNavigation(
                    navigation = HomeFragmentDirections.actionHomeFragmentToInputPasswordFragment(
                        VALIDATE_PASSWORD
                    ), actionId = OPEN_NEW_SCREEN
                )
            )
        }
    }

    override fun onSwipeDelete(position: Int, data: Any?, calledFor: Int) {
        if (data is UiRecord) {
            this.lastClickedPosition = position
            this.lastSavedUiRecord = data
            this.deletedItems?.add(data)
            this.uiListingData.removeAt(position)
            if (uiListingData.size == 1) {
                uiListingData.clear()
                updateAdapter = true
            } else {
                passwordListingAdapter?.notifyItemRemoved(position)
            }
            errorViewType = SHOW_ADD_NEW_RECORD
            noRecord = uiListingData.isEmpty()
            triggerEvent(SHOW_SNACK_BAR_WITH_ACTION)
        }
    }

    fun addNewRecord() {
        if (isPerformClick()) {
            deleteRecordFromDb()
            switchScreen(
                CustomNavigation(
                    HomeFragmentDirections.actionHomeFragmentToDialogEnterDetails(
                        INSERT_PASSWORD
                    ), OPEN_NEW_SCREEN
                )
            )
        }
    }

    private fun getUiData() {
        searchChange.value = ""
    }

    fun refreshListing() {
        if (lastSavedUiRecord?.isHeader == true) {
            if (lastSavedUiRecord?.showPassword == true) {
                changeShowPassword(false)
            } else {
                changeShowPassword(true)
            }
        } else {
            lastSavedUiRecord?.apply {
                showPassword = !showPassword
            }
            uiListingData[0].showPassword = true
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

    fun updateData(it: List<UiRecord>?) {
        if (it == null || it.isEmpty()) {
            noRecord = true
            uiListingData.clear()
        } else {
            uiListingData.clear()
            uiListingData.add(getHeader())
            uiListingData.addAll(it)
            noRecord = uiListingData.isEmpty()
        }
        updateAdapter = true
    }

    private fun getHeader(): UiRecord {
        return UiRecord(
            id = 0,
            title = customApplication.getString(R.string.label_title),
            userName = customApplication.getString(R.string.label_user_name),
            isHeader = true,
            showPassword = false
        )
    }

    fun searchUserInput(userSearch: String?) {
        if (userSearch.isNullOrBlank()) {
            errorViewType = SHOW_ADD_NEW_RECORD
            deleteRecordFromDb()
            getUiData()
        } else {
            errorViewType = SHOW_NO_SEARCH_FOUND
            searchChange.value = userSearch
        }
    }


    fun undoDeleteAction() {
        lastSavedUiRecord?.let {
            if (uiListingData.isEmpty()) {
                uiListingData.add(0, getHeader())
                uiListingData.add(lastClickedPosition, it)
                updateAdapter = true
            } else {
                uiListingData.add(lastClickedPosition, it)
                passwordListingAdapter?.notifyItemInserted(lastClickedPosition)
            }
            noRecord = false
            deletedItems?.remove(it)
        }
    }

    override fun onCleared() {
        deleteRecordFromDb()
        super.onCleared()
    }

    fun deleteRecordFromDb() {
        repository.removeRecords(deletedItems)
    }

}