package com.remember.password.util

import androidx.room.Room
import com.remember.password.database.AppDatabase
import com.remember.password.repository.Repository
import com.remember.password.view.enterdetails.viewmodel.EnterDetailViewModel
import com.remember.password.view.home.viewmodel.HomeViewModel
import com.remember.password.view.inputpassword.viewmodel.InputPasswordViewModel
import com.remember.password.view.landing.viewmodel.LandingViewModel
import com.remember.password.view.masterpassword.viewmodel.MasterPasswordViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

private val basicDependency = module {

    viewModel {
        HomeViewModel(androidApplication(), get())
    }

    viewModel {
        InputPasswordViewModel(androidApplication(), get())
    }

    viewModel {
        EnterDetailViewModel(androidApplication(), get())
    }

    viewModel {
        LandingViewModel(androidApplication(), get())
    }

    viewModel {
        MasterPasswordViewModel(androidApplication(), get())
    }

    factory {
        Repository(get())
    }

    single {
        SharedPreferenceUtils(androidApplication())
    }

    single {
        Room.databaseBuilder(
            androidApplication()
            , AppDatabase::class.java
            , "password_record_db"
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }
}

val appComponent: List<Module> = listOf(basicDependency)