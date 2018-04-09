package com.m2f.sheddtest.data.features.search

import com.m2f.sheddtest.data.extensions.observe
import com.m2f.sheddtest.domain.features.search.model.TopicImage
import com.twitter.sdk.android.core.services.SearchService
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * @author Marc Moreno
 * @since 8/4/18.
 */
class TwitterDatasource
@Inject constructor(private val searchService: SearchService) : TopicDatasource {

    override fun findImages(topic: String): Flowable<List<TopicImage>> = searchService.tweets(topic,
            null,
            "en",
            "en_GB",
            null,
            30,
            null,
            null,
            null,
            true)
            .observe()
            .map { it.tweets //notice that this functional programming methos are from kotlin, not Rx
                    .filter { it.entities.media.isNotEmpty() } //we just want tweets with images
                    .flatMap { it.entities.media } //then we flatten the array of images of each tweet
                    .map { TopicImage(it.mediaUrl) }} //to add the in the resulting list of images
}