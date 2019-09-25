package com.remember.password.view.enterdetails

import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.Observer
import com.remember.password.R
import com.remember.password.base.BaseDialogFragment
import com.remember.password.databinding.DialogEnterDetailsBinding
import com.remember.password.view.enterdetails.viewmodel.EnterDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class DialogEnterDetails : BaseDialogFragment<DialogEnterDetailsBinding
        , EnterDetailViewModel>() {

    private val enterDetailViewModel: EnterDetailViewModel by viewModel()

    override fun getViewToInflate(): Int {
        return R.layout.dialog_enter_details
    }

    override fun getViewModel(): EnterDetailViewModel? {
        return enterDetailViewModel
    }

    override fun actionAfterViewInflated() {
        enterDetailViewModel.triggerEvent.observe(this, Observer {
            if (it == Constants.CLOSE_DIALOG) {
                dismiss()
                enterDetailViewModel.triggerEvent(Constants.INVALID_ACTION)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        val wlp = dialog?.window?.attributes
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        if (wlp != null) {
            wlp.gravity = Gravity.TOP
            wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()
            dialog?.window?.attributes = wlp
        }
    }
}