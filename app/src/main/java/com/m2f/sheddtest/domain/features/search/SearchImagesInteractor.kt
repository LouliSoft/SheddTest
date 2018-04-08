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
                    private val executor: Executor,
                    private val topicRepository: TopicRepository) {

    operator fun invoke(topic: String): Flowable<List<TopicImage>> =
            topicRepository.findImages(topic)
                    .subscribeOn(Schedulers.from(executor))
                    .observeOn(postExecutionThread.scheduler)
}