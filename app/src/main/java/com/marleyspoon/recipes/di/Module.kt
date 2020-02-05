package com.marleyspoon.recipes.di

import com.marleyspoon.recipes.data.DataRepository
import com.marleyspoon.recipes.viewmodel.SharedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

// declared ViewModel using the viewModel keyword
val module: Module = module {
    viewModel { SharedViewModel(get()) }
    single { DataRepository() }
}