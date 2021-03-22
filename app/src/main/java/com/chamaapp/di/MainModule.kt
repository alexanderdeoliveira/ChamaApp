package com.chamaapp.di

import com.chamaapp.R
import com.chamaapp.datasource.PlacesDataSource
import com.chamaapp.viewmodel.PlacesViewModel
import com.google.android.libraries.places.api.Places
import org.koin.dsl.module
import org.koin.android.viewmodel.dsl.viewModel

val mainModule = module {
    viewModel { PlacesViewModel() }
}