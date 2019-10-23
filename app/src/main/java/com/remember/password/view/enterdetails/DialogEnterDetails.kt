package com.remember.password.view.enterdetails

import androidx.lifecycle.Observer
import com.remember.password.R
import com.remember.password.base.BaseDialogFragment
import com.remember.password.databinding.DialogEnterDetailsBinding
import com.remember.password.util.CLOSE_DIALOG
import com.remember.password.util.INVALID_ACTION
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
            if (it == CLOSE_DIALOG) {
                dismiss()
                enterDetailViewModel.triggerEvent(INVALID_ACTION)
            }
        })
    }

}