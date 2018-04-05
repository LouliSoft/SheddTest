package com.m2f.sheddtest.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.flyshionista.flyshionistaandroid.main.di.viewmodelinjection.ViewModelKey
import com.m2f.sheddtest.di.viewmodelinjection.ViewModelFactory
import com.m2f.sheddtest.presentation.features.search.SearchViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
class ViewModelModule {

    @Provides
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    fun providesSearchViewModel(searchViewModel: SearchViewModel): ViewModel = searchViewModel

    @Provides
    @Singleton
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory = factory

}