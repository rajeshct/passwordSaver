package com.remember.password.view.inputpassword

import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.remember.password.R
import com.remember.password.base.BaseDialogFragment
import com.remember.password.databinding.DialogInputPasswordBinding
import com.remember.password.util.CLOSE_DIALOG_WITH_SUCCESS
import com.remember.password.util.REFRESH_PASSWORD_LISTING
import com.remember.password.util.SHOW_SNACK_BAR
import com.remember.password.view.inputpassword.viewmodel.InputPasswordViewModel
import com.remember.password.view.landing.viewmodel.HomeScreenViewModel
import kotlinx.android.synthetic.main.dialog_input_password.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class InputPasswordFragment :
    BaseDialogFragment<DialogInputPasswordBinding, InputPasswordViewModel>() {
    private val inputPasswordViewModel: InputPasswordViewModel by viewModel()
    private val homeScreenViewModel: HomeScreenViewModel by sharedViewModel()

    override fun getViewToInflate(): Int {
        return R.layout.dialog_input_password
    }

    override fun getViewModel(): InputPasswordViewModel? {
        return inputPasswordViewModel
    }

    override fun actionAfterViewInflated() {
        inputPasswordViewModel.triggerEvent.observe(viewLifecycleOwner, Observer {
            if (it == CLOSE_DIALOG_WITH_SUCCESS) {
                homeScreenViewModel.refreshListing.value = REFRESH_PASSWORD_LISTING
                dismiss()
            } else if (it == SHOW_SNACK_BAR) {
                Snackbar.make(
                    constraint_layout,
                    getString(R.string.error_master_pwd_match),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}