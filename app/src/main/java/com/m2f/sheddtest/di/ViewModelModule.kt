package com.m2f.sheddtest.di

import android.arch.lifecycle.ViewModelProvider
import com.m2f.sheddtest.di.viewmodelinjection.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule {

    @Provides
    @Singleton
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory = factory

}