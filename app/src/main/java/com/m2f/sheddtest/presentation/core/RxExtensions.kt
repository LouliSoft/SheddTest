package com.m2f.sheddtest.presentation.core

import android.databinding.Observable
import android.databinding.ObservableField
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import android.databinding.Observable as DatabindingObservable


/**
 * @author Marc Moreno
 * @since 5/4/18.
 */

infix fun <E : FlowableEmitter<*>> E.canEmitt(block: E.() -> Unit) {
    if (this.isCancelled.not()) {
        block()
    }
}

//this allows us to observe a Databinding Observable as a Rx Flowable
private inline fun <reified T : Observable, R : Any> T.observe(crossinline block: (T) -> R): Flowable<R> =
        Flowable.create({ emitter ->
            emitter canEmitt {
                object : Observable.OnPropertyChangedCallback() {
                    override fun onPropertyChanged(observable: Observable, id: Int) = try {
                        onNext(block(observable as T))
                    } catch (e: Exception) {
                        emitter.onError(e)
                    }
                }.let {
                    setCancellable { removeOnPropertyChangedCallback(it) }
                    addOnPropertyChangedCallback(it)
                }
            }
        }, BackpressureStrategy.LATEST)

fun <T : Any> ObservableField<T>.observe(): Flowable<T> = observe { it.get()!! }.share()
