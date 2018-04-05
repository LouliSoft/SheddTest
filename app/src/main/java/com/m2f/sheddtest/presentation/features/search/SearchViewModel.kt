package com.m2f.sheddtest.presentation.features.search

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.util.Log
import com.m2f.sheddtest.domain.features.search.SearchImagesInteractor
import com.m2f.sheddtest.presentation.core.observe
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * @author Marc Moreno
 * @since 5/4/18.
 */
class SearchViewModel
@Inject constructor(private val searchImagesInteractor: SearchImagesInteractor) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val searchText = ObservableField("")

    init {
        compositeDisposable += searchText.observe()
                .filter { it.isNotBlank() } //we just want non empty text
                .distinctUntilChanged() //this prevent us to make the same call 2 times in a row
                .debounce(350L, TimeUnit.MILLISECONDS) //we define a time window of 350 millis to prevent sending every text written.
                .switchMap { searchImagesInteractor(it) } //we use switchmap here because we are writing alot of text and we want to stop making the previous search if the input changes
                .subscribeBy(onNext = { it.forEachIndexed { index, topicImage -> Log.d("Search_text_$index", topicImage.image) } })
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}