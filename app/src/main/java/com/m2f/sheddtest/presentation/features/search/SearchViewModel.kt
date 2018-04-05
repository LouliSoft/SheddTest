package com.m2f.sheddtest.presentation.features.search

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.util.Log
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
@Inject constructor() : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val searchText = ObservableField("")


    init {
        compositeDisposable += searchText.observe()
                .debounce(350L, TimeUnit.MILLISECONDS) //we define a time window of 350 millis to prevent sending every text written.
                .subscribeBy(onNext = { Log.d("Search_text", it) })
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}