package com.m2f.sheddtest.domain.features.search

import com.m2f.sheddtest.domain.PostExecutionThread
import com.m2f.sheddtest.domain.features.search.model.TopicImage
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor
import javax.inject.Inject

/**
 * @author Marc Moreno
 * @since 5/4/18.
 */
class SearchImagesInteractor
@Inject constructor(private val postExecutionThread: PostExecutionThread,
                    private val executor: Executor) {

    operator fun invoke(topic: String): Flowable<List<TopicImage>> =
            Flowable.just(listOf(TopicImage("https://goo.gl/images/nLy5DP"))) //random image
                    .subscribeOn(Schedulers.from(executor))
                    .observeOn(postExecutionThread.scheduler)
}