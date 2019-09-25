package com.remember.password.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.remember.password.BR

abstract class BaseDiAdapter<T> : RecyclerView.Adapter<BaseDiAdapter.BaseViewHolder>() {

    private var clickCallback: IClickCallback<*>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), viewType, parent, false
        )
        return BaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.refreshRow(position, getValueAtIndex(position), clickCallback)
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdAtPosition(position)
    }

    abstract fun getLayoutIdAtPosition(position: Int): Int

    abstract fun getValueAtIndex(position: Int): T

    /**
     * Register for click Callback
     * @param clickCallback IClickCallback
     */
    fun registerClickCallBack(clickCallback: IClickCallback<*>) {
        if (this.clickCallback == null) {
            this.clickCallback = clickCallback
        }
    }

    /**
     * When ever view is destroyed remove callBack.
     */
    fun unregisterClickCallBack() {
        this.clickCallback = null
    }

    class BaseViewHolder(private val viewDataBinding: ViewDataBinding) :

        RecyclerView.ViewHolder(viewDataBinding.root) {

        fun <T> refreshRow(position: Int, valueAtIndex: T, clickCallback: IClickCallback<*>?) {
            viewDataBinding.setVariable(BR.data, valueAtIndex)
            viewDataBinding.setVariable(BR.position, position)
            viewDataBinding.setVariable(BR.clickCallBack, clickCallback)
            viewDataBinding.executePendingBindings()
        }
    }

    interface IClickCallback<T> {
        /**
         *
         * @param position Int current row
         * @param tag Int if their are different types of clicks (say their are three different button in row)
         * @param data Any if you require data in current position
         * @param calledFor Int if multiple recyclerView are their and you need to create single callback
         */
        fun onClick(position: Int = 0, tag: Int = 0, data: T? = null, calledFor: Int = 0)
    }
}