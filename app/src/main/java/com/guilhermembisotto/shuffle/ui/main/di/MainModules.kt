package com.guilhermembisotto.shuffle.ui.main.di

import com.guilhermembisotto.shuffle.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val mainViewModelModule = module {
    viewModel { MainViewModel(get()) }
}

fun getMainModules() = listOf(mainViewModelModule)
