package com.m2f.sheddtest.di

import com.m2f.sheddtest.data.executor.JobExecutor
import com.m2f.sheddtest.data.features.search.*
import com.m2f.sheddtest.data.persistency.ExpiringLruCache
import com.m2f.sheddtest.domain.PostExecutionThread
import com.m2f.sheddtest.domain.features.search.TopicRepository
import com.m2f.sheddtest.domain.features.search.model.TopicImage
import com.m2f.sheddtest.presentation.core.MainThread
import com.twitter.sdk.android.core.TwitterApiClient
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.services.SearchService
import dagger.Module
import dagger.Provides
import io.reactivex.processors.BehaviorProcessor
import java.util.concurrent.*
import javax.inject.Named
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
    fun providesSearchService(apiClient: TwitterApiClient): SearchService = apiClient.searchService

    @Provides
    @Singleton
    fun providesDatasources(twitterDatasource: TwitterDatasource, facebookTopicDatasource: FacebookTopicDatasource, dummyTopicDatasource: DummyTopicDatasource): Array<TopicDatasource> = arrayOf(twitterDatasource, dummyTopicDatasource)

    @Provides
    @Singleton
    fun providesTopicRepository(topicRepositoryImpl: TopicRepositoryImpl): TopicRepository = topicRepositoryImpl

    //this cache only lasts for 10 minutes. As this is a cache is just working while the app is alive!
    @Provides
    @Singleton
    fun providesCache(): ExpiringLruCache<String, List<TopicImage>> = ExpiringLruCache(100, TimeUnit.MINUTES.toMillis(10))

    @Provides
    @Singleton
    @Named("History")
    fun providesHistory(cache: ExpiringLruCache<String, List<TopicImage>>): BehaviorProcessor<List<String>> = cache.keys
}