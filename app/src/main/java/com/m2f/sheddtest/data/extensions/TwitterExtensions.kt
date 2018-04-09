package com.m2f.sheddtest.data.extensions

import com.m2f.sheddtest.presentation.core.extensions.canEmitt
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import retrofit2.Call

/**
 * @author Marc Moreno
 * @since 8/4/18.
 */

inline fun <reified T> Call<T>.observe(): Flowable<T> = Flowable.defer {
    Flowable.create<T> ({ emitter ->
            object : Callback<T>() {
                override fun success(result: Result<T>) {
                    emitter canEmitt {
                        onNext(result.data)
                        onComplete()
                    }
                }

                override fun failure(exception: TwitterException) {
                    emitter canEmitt {
                        onError(exception)
                    }
                }

            }.let {
                enqueue(it)
                emitter.setCancellable { cancel() } //if we cancel the subscription the we cancel the request
            }
    }, BackpressureStrategy.LATEST)
}