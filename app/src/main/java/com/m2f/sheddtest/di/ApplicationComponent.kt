package com.m2f.sheddtest.di

import android.content.Context
import com.m2f.sheddtest.presentation.main.SheddApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AndroidInjectionModule::class,
        ApplicationModule::class,
        NetworkModule::class,
        ActivityModule::class))
interface ApplicationComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(application: Context): Builder

        fun build(): ApplicationComponent
    }

    fun inject(app: SheddApplication)

}