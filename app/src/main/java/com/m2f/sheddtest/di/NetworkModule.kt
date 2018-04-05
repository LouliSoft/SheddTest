package com.m2f.sheddtest.di

import android.content.Context
import com.m2f.sheddtest.BuildConfig
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providesChuckInterceptor(context: Context): ChuckInterceptor = ChuckInterceptor(context.applicationContext)

    @Provides
    @Singleton
    fun providesHttpClient(context: Context, chuckInterceptor: ChuckInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .apply {
                    if (BuildConfig.DEBUG) {
                        addNetworkInterceptor(chuckInterceptor)
                    }
                    cache(Cache(context.getDir("network_cache", Context.MODE_PRIVATE), 10 * 1024 * 1024))
                }
                .build()
    }

}