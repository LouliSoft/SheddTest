package com.m2f.sheddtest.presentation.main

import android.app.Activity
import android.app.Application
import android.os.StrictMode
import com.m2f.sheddtest.BuildConfig
import com.m2f.sheddtest.di.initInjection
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig
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
        Twitter.initialize(TwitterConfig.Builder(this)
                .twitterAuthConfig(TwitterAuthConfig("83qYjCuvllfOEk8OeHi7w","LIFKEAIw7VZfojW0d5ZYRuBksHtEqCjr8FtzlJHvEtw"))
                .debug(BuildConfig.DEBUG).build())
    }

}