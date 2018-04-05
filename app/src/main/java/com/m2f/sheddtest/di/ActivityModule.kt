package com.m2f.sheddtest.di

import com.m2f.sheddtest.presentation.features.search.SearchActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * here is where we are going to put all the inectable activities
 */
@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun searchActivity(): SearchActivity
}