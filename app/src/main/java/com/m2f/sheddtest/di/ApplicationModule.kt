package com.m2f.sheddtest.di

import com.m2f.sheddtest.data.executor.JobExecutor
import com.m2f.sheddtest.domain.PostExecutionThread
import com.m2f.sheddtest.presentation.core.MainThread
import com.twitter.sdk.android.core.TwitterApiClient
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.services.StatusesService
import dagger.Module
import dagger.Provides
import java.util.concurrent.*
import javax.inject.Singleton

@Module(includes = arrayOf(ViewModelModule::class))
class ApplicationModule {

    @Provides
    @Singleton
    fun providesPostExecutionThread(): PostExecutionThread = MainThread()

    @Provides
    @Singleton
    fun prividesThreadPoolExecutor(): ThreadPoolExecutor = ThreadPoolExecutor(
            3,
            5,
            10,
            TimeUnit.SECONDS,
            LinkedBlockingQueue<Runnable>(),
            JobThreadFactory())

    @Provides
    @Singleton
    fun providesJobExecutor(jobExecutor: JobExecutor): Executor = jobExecutor

    private class JobThreadFactory : ThreadFactory {
        private var counter = 0

        override fun newThread(runnable: Runnable): Thread {
            return Thread(runnable, "android_" + counter++)
        }
    }

    @Provides
    @Singleton
    fun providesTwitterApiClient(): TwitterApiClient = TwitterCore.getInstance().apiClient

    @Provides
    @Singleton
    fun providesStatusesService(apiClient: TwitterApiClient): StatusesService = apiClient.statusesService
}