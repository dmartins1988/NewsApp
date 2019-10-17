package com.example.newsapp.di.module

import com.example.newsapp.presentation.ui.main.NewsViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val ViewModelModule = module {
    viewModel { NewsViewModel(get()) }
}