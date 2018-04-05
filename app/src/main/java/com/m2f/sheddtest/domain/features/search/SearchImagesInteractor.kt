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
            Flowable.just(
                    listOf(TopicImage("http://www.gregorybufithis.com/wp-content/uploads/2016/05/random-numbers.jpg"),
                            TopicImage("http://scruss.com/wordpress/wp-content/uploads/2013/06/random20130606210630.png"),
                            TopicImage("https://yt3.ggpht.com/-H7Ofqi47o70/AAAAAAAAAAI/AAAAAAAAAAA/cTBdlRrGTMU/s900-c-k-no-mo-rj-c0xffffff/photo.jpg"),
                            TopicImage("https://k30.kn3.net/taringa/7/7/9/8/3/D/guitar_gero/2B1.jpg"))) //random image
                    .subscribeOn(Schedulers.from(executor))
                    .observeOn(postExecutionThread.scheduler)
}