package com.remember.password.base

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.remember.password.BR
import com.remember.password.util.CustomNavigation

abstract class BaseDialogFragment<VB : ViewDataBinding, VM : BaseViewModel>
    : AppCompatDialogFragment(), IViewRequired<VM> {

    private lateinit var binding: VB

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getViewToInflate(), container, false)
        binding.setVariable(BR.viewModel, getViewModel())
        getViewModel()?.setArguments(arguments)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.executePendingBindings()
        actionAfterViewInflated()
        observeChanges()
    }

    private fun observeChanges() {
        getViewModel()?.getSwitchScreen()?.observe(this, Observer {
            if (it == null) {
                return@Observer
            }
            switchScreenChange(it)
            getViewModel()?.switchScreen(null)
        })
    }

    private fun switchScreenChange(it: CustomNavigation) {
        if (requireActivity() is BaseActivity<*, *>) {
            (requireActivity() as BaseActivity<*, *>).uiScreenNavigation(it)
        }
    }
}