package com.m2f.sheddtest

import com.m2f.sheddtest.domain.features.search.model.TopicImage
import com.m2f.sheddtest.presentation.core.extensions.invoke
import com.m2f.sheddtest.presentation.core.extensions.observe
import com.m2f.sheddtest.presentation.features.search.SearchViewModel
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

/**
 * @author Marc Moreno
 * @since 7/4/18.
 */
class SearchViewModelTest {

    @Nested
    @DisplayName("Given a SearchViewModel instance")
    inner class SearchViewModelInstance {

        val viewModel = SearchViewModel(mock {
            on {
                invoke(any())
            } doReturn Flowable.just(
                    listOf(TopicImage("http://www.gregorybufithis.com/wp-content/uploads/2016/05/random-numbers.jpg"),
                            TopicImage("http://scruss.com/wordpress/wp-content/uploads/2013/06/random20130606210630.png"),
                            TopicImage("https://yt3.ggpht.com/-H7Ofqi47o70/AAAAAAAAAAI/AAAAAAAAAAA/cTBdlRrGTMU/s900-c-k-no-mo-rj-c0xffffff/photo.jpg"),
                            TopicImage("https://k30.kn3.net/taringa/7/7/9/8/3/D/guitar_gero/2B1.jpg"))) //random image
        })

        @Nested
        @DisplayName("When we write some text")
        inner class WhenWriteSomeText {

            @Test
            @DisplayName("If we write and empty string then nothing happens")
            fun noEmpty() {

                val testSubscriber = TestSubscriber<Boolean>()
                viewModel.isResultEmpty.observe()
                        .subscribe(testSubscriber)

                viewModel.searchText("")

                testSubscriber.awaitCount(0)
                        .assertNoErrors()
                        .assertValueCount(0)

                testSubscriber.dispose()
            }

            @Test
            @DisplayName("If we write random then we get a list of 4 images")
            fun resultList() {

                val testSubscriber = TestSubscriber<List<TopicImage>>()
                viewModel.topicList.observe()
                        .subscribe(testSubscriber)

                viewModel.searchText("random")

                testSubscriber.awaitCount(1)
                        .assertNoErrors()
                        .assertValueCount(1)
                        .assertValue { it.count() == 4 }

                testSubscriber.dispose()
            }

            @Test
            @DisplayName("If we type 4 different texts in a row then we just get 1 output")
            fun resultListWith4TextsInaRow() {

                //this is because of the debounce that we must get just 1 result
                val testSubscriber = TestSubscriber<List<TopicImage>>()
                viewModel.topicList.observe()
                        .subscribe(testSubscriber)

                viewModel.searchText("random")
                viewModel.searchText("random1")
                viewModel.searchText("random2")
                viewModel.searchText("random3")

                testSubscriber.awaitCount(1)
                        .assertNoErrors()
                        .assertValueCount(1)
                        .assertValue { it.count() == 4 }

                testSubscriber.dispose()
            }
        }

    }
}