package com.remember.password.base

import android.app.Application
import android.os.Bundle
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.remember.password.util.CustomNavigation

abstract class BaseViewModel(application: Application) : AndroidViewModel(application)
    , Observable, LifecycleObserver {

    val triggerEvent = MutableLiveData<Int>()

    private val switchScreen = MutableLiveData<CustomNavigation>()

    @Transient
    private var mCallbacks: PropertyChangeRegistry? = null

    /**
     * All arguments passed while opening activity or fragment
     * @param bundle Bundle?
     */
    abstract fun setArguments(bundle: Bundle?)

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        synchronized(this) {
            if (mCallbacks == null) {
                mCallbacks = PropertyChangeRegistry()
            }
        }
        mCallbacks?.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks?.remove(callback)
    }

    /**
     * Notifies listeners that all properties of this instance have changed.
     */
    fun notifyChange() {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks?.notifyCallbacks(this, 0, null)
    }

    /**
     * Notifies listeners that a specific property has changed. The getter for the property
     * that changes should be marked with [Bindable] to generate a field in
     * `BR` to be used as `fieldId`.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    fun notifyPropertyChanged(fieldId: Int) {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks?.notifyCallbacks(this, fieldId, null)
    }

    /**
     *
     * @param action Int action id
     */
    fun triggerEvent(action: Int) {
        try {
            triggerEvent.value = action
        } catch (exp: Exception) {
            triggerEvent.postValue(action)
        }
    }

    fun getSwitchScreen(): LiveData<CustomNavigation> {
        return switchScreen
    }


    fun switchScreen(customNavigation: CustomNavigation) {
        switchScreen.value = customNavigation
    }
}