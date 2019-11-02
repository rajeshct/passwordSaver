package com.remember.password.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.remember.password.BR

abstract class BaseDiAdapter<P> : RecyclerView.Adapter<BaseDiAdapter.BaseViewHolder>() {

    private var clickCallback: IClickCallback? = null

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

    abstract fun getValueAtIndex(position: Int): P

    /**
     * Register for click Callback
     * @param clickCallback IClickCallback
     */
    fun registerClickCallBack(clickCallback: IClickCallback) {
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

    /**
     * This function will call whenever user perform swipe left|right
     */
    fun deleteItem(position: Int) {
        clickCallback?.onSwipeDelete(position, getValueAtIndex(position))
    }

    class BaseViewHolder(private val viewDataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        var isHeader = false

        fun <T> refreshRow(
            position: Int,
            valueAtIndex: T,
            clickCallback: IClickCallback?
        ) {
            viewDataBinding.setVariable(BR.data, valueAtIndex)
            viewDataBinding.setVariable(BR.position, position)
            viewDataBinding.setVariable(BR.clickCallBack, clickCallback)
            viewDataBinding.executePendingBindings()
        }
    }

    interface IClickCallback {
        /**
         *
         * @param position Int current row
         * @param tag Int if their are different types of clicks (say their are three different button in row)
         * @param data Any if you require lastSavedUiRecord in current position
         * @param calledFor Int if multiple recyclerView are their and you need to create single callback
         */
        fun onClick(position: Int = 0, tag: Int = 0, data: Any? = null, calledFor: Int = 0)

        /**
         * This function will trigger whenever user press swipe left/right
         * @param position position where user swiped
         * @param data lastSavedUiRecord at current index
         * @param calledFor Int if multiple recyclerView are their and you need to create single callback
         */
        fun onSwipeDelete(position: Int, data: Any? = null, calledFor: Int = 0)
    }
}