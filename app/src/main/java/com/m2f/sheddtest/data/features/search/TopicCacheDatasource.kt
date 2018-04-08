package com.m2f.sheddtest.data.features.search

import com.m2f.sheddtest.data.persistency.ExpiringLruCache
import com.m2f.sheddtest.domain.features.search.model.TopicImage
import com.m2f.sheddtest.presentation.core.extensions.canEmitt
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Marc Moreno
 * @since 8/4/18.
 */
@Singleton
class TopicCacheDatasource
@Inject constructor() : TopicDatasource {

    //this cache only lasts for 10 minutes. As this is a cache is just working while the app is alive!
    private val cache = ExpiringLruCache<String, List<TopicImage>>(1, TimeUnit.MINUTES.toMillis(10))

    override fun findImages(topic: String): Flowable<List<TopicImage>> = Flowable.defer {
        Flowable.create<List<TopicImage>>({ emitter ->
            emitter canEmitt {
                cache[topic]?.let { onNext(it) }
                onComplete()
            }
        }, BackpressureStrategy.DROP)
    }

    fun storeImages(topic: String, images: List<TopicImage>) {
        cache[topic] = images
    }
}