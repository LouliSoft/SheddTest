package com.m2f.sheddtest.data.features.search

import com.m2f.sheddtest.domain.features.search.TopicRepository
import com.m2f.sheddtest.domain.features.search.model.TopicImage
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * @author Marc Moreno
 * @since 8/4/18.
 */
class TopicRepositoryImpl
@Inject constructor(vararg val datasources: TopicDatasource,
                    private val topicCacheDatasource: TopicCacheDatasource) : TopicRepository {

    //this will get all the datasources and will merge them all in one single flow so we can set as many sources as we want
    override fun findImages(topic: String): Flowable<List<TopicImage>> =
            Flowable.merge(topicCacheDatasource.findImages(topic), //now we have a cache source
                    Flowable.merge(datasources.map { it.findImages(topic) })) //and the merge of the N datasources
                    .firstElement() //so we get the first datasource that emits something (if cache have something it'll be this for sure)
                    .toFlowable()
}