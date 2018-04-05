package com.m2f.sheddtest.presentation.main

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    //this function let us get the viewModel Injected!!!
    inline fun <reified VM : ViewModel> getViewModel(): VM = ViewModelProviders.of(this, viewModelFactory).get(VM::class.java)

}