package com.m2f.sheddtest

import com.m2f.sheddtest.data.executor.JobExecutor
import com.m2f.sheddtest.data.features.search.DummyTopicDatasource
import com.m2f.sheddtest.data.features.search.TopicRepositoryImpl
import com.m2f.sheddtest.domain.PostExecutionThread
import com.m2f.sheddtest.domain.features.search.SearchImagesInteractor
import com.m2f.sheddtest.domain.features.search.TopicRepository
import com.m2f.sheddtest.domain.features.search.model.TopicImage
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.TestSubscriber
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import javax.inject.Named

/**
 * @author Marc Moreno
 * @since 9/4/18.
 */
class SearchInteractorTest {


    @Nested
    @DisplayName("Given a searchImagesInteractor instance")
    inner class InteractorInstance {

        private val threadPoolEecutor = ThreadPoolExecutor(
                3,
                5,
                10,
                TimeUnit.SECONDS,
                LinkedBlockingQueue<Runnable>())

        private val topicRepo: TopicRepository = TopicRepositoryImpl(
                mock {
                    on {
                        findImages(any())
                    } doReturn Flowable.just(
                            listOf(TopicImage("http://www.gregorybufithis.com/wp-content/uploads/2016/05/random-numbers.jpg"),
                                    TopicImage("http://scruss.com/wordpress/wp-content/uploads/2013/06/random20130606210630.png"),
                                    TopicImage("https://yt3.ggpht.com/-H7Ofqi47o70/AAAAAAAAAAI/AAAAAAAAAAA/cTBdlRrGTMU/s900-c-k-no-mo-rj-c0xffffff/photo.jpg"),
                                    TopicImage("https://k30.kn3.net/taringa/7/7/9/8/3/D/guitar_gero/2B1.jpg")))
                }, DummyTopicDatasource(),
                topicCacheDatasource = mock {})

        private val searchImagesInteractor = SearchImagesInteractor(object : PostExecutionThread {
            override val scheduler: Scheduler = Schedulers.trampoline()

        },
                JobExecutor(threadPoolEecutor), topicRepo)

        @Nested
        @DisplayName("When searching for something")
        inner class WhenSearchForSomething {

            @Test
            @DisplayName("Then we should have two emited events that are the result of scaning both datasources")
            fun shouldEmittTowTimes() {
                val testSubscriber = TestSubscriber<List<TopicImage>>()
                searchImagesInteractor("random")
                        .subscribe(testSubscriber)

                testSubscriber.awaitCount(2)
                        .assertNoErrors()
                        .assertValueCount(2)
                        .assertComplete()

                testSubscriber.dispose()
            }
        }
    }
}