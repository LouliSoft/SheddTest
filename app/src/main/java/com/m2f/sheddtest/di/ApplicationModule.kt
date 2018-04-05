package com.m2f.sheddtest.di

import com.m2f.sheddtest.domain.PostExecutionThread
import com.m2f.sheddtest.presentation.core.MainThread
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = arrayOf(ViewModelModule::class))
class ApplicationModule {

    @Provides
    @Singleton
    fun providesPostExecutionThread(): PostExecutionThread = MainThread()
}