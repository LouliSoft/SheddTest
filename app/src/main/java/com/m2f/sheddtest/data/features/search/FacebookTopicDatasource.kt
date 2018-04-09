package com.m2f.sheddtest.data.features.search

import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.HttpMethod
import com.m2f.sheddtest.domain.features.search.model.TopicImage
import com.m2f.sheddtest.presentation.core.extensions.canEmitt
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * @author Marc Moreno
 * @since 8/4/18.
 */
class FacebookTopicDatasource
    @Inject constructor(): TopicDatasource {

    //this api call isn't really working (WIP)
    override fun findImages(topic: String): Flowable<List<TopicImage>> =
    Flowable.defer {
        Flowable.create<List<TopicImage>>({ emitter ->
            emitter canEmitt {
                GraphRequest.Callback {
                    if (it.error == null) {
                        val list = mutableListOf<TopicImage>()
                        it.jsonArray?.let {
                            for (i in 0 until it.length()) {
                                if (it.getJSONObject(i).isNull("full_picture")) {
                                    try {
                                        list.add(TopicImage(it.getJSONObject(i).getString("full_picture")))
                                    } finally {

                                    }
                                }
                            }
                        }
                        onNext(list)
                        onComplete()
                    } else {
                        emitter canEmitt {
                            onError(it.error.exception)
                        }
                    }
                }.also {
                    GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/$topic/feed",
                        null,
                        HttpMethod.GET,
                        it).executeAsync()}
            }
        }, BackpressureStrategy.DROP)
    }
}