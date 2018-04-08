package com.m2f.sheddtest.domain.features.search

import com.m2f.sheddtest.domain.features.search.model.TopicImage
import io.reactivex.Flowable

/**
 * @author Marc Moreno
 * @since 8/4/18.
 */
interface TopicRepository {

    fun findImages(topic: String): Flowable<List<TopicImage>>
}