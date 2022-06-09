package com.example.nasagridapp.app.di

import com.example.nasagridapp.app.ui.details.adapter.ImageDetailsAdapter
import com.example.nasagridapp.app.ui.grid.adapter.ImageGridAdapter
import com.example.nasagridapp.app.ui.grid.viewmodel.ImageGridViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
   viewModel { ImageGridViewModel() }
}

val adapterModule = module {
    single { ImageGridAdapter(get(),get()) }
    single { ImageDetailsAdapter() }
}