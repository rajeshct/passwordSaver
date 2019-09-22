package com.remember.password.util

import com.remember.password.view.home.viewmodel.HomeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

private val basicDependency = module {
    viewModel {
        HomeViewModel(androidApplication())
    }
}

val appComponent: List<Module> = listOf(basicDependency)