package com.m2f.sheddtest.presentation.main

import android.app.Activity
import android.app.Application
import android.os.StrictMode
import com.m2f.sheddtest.BuildConfig
import com.m2f.sheddtest.di.initInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * @author Marc Moreno
 * @since 5/4/18.
 */
class SheddApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Activity>

    override fun activityInjector() = injector

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().apply {
                detectAll()
                penaltyLog()
            }.build())

            StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().apply {
                detectAll()
                penaltyLog()
            }.build())

        }

        initInjection()
    }

}