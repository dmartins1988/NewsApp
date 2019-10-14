package com.example.newsapp.di.module

import com.example.newsapp.presentation.ui.main.NewsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelModule = module {
    viewModel { NewsViewModel(get(), get()) }
}