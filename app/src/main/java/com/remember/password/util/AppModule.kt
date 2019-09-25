package com.remember.password.util

import androidx.lifecycle.LifecycleOwner
import androidx.room.Room
import com.remember.password.database.AppDatabase
import com.remember.password.repository.Repository
import com.remember.password.view.enterdetails.viewmodel.EnterDetailViewModel
import com.remember.password.view.home.viewmodel.HomeViewModel
import com.remember.password.view.landing.viewmodel.LandingViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

private val basicDependency = module {

    viewModel { (lifecycleOwner: LifecycleOwner) ->
        HomeViewModel(androidApplication(), get(), lifecycleOwner)
    }

    viewModel {
        EnterDetailViewModel(androidApplication(), get())
    }

    viewModel {
        LandingViewModel(androidApplication())
    }

    factory {
        Repository(get())
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