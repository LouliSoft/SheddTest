package com.m2f.sheddtest.data.features.search

import com.m2f.sheddtest.domain.features.search.model.TopicImage
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * @author Marc Moreno
 * @since 9/4/18.
 */
class DummyTopicDatasource
@Inject constructor() : TopicDatasource {
    override fun findImages(topic: String): Flowable<List<TopicImage>> = Flowable.just(listOf(TopicImage("http://www.gregorybufithis.com/wp-content/uploads/2016/05/random-numbers.jpg")))
}