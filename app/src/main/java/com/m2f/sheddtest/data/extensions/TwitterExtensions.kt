package com.m2f.sheddtest.data.extensions

import com.m2f.sheddtest.presentation.core.extensions.canEmitt
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import io.reactivex.Single
import retrofit2.Call

/**
 * @author Marc Moreno
 * @since 8/4/18.
 */

inline fun <reified T> Call<T>.observe(): Single<T> = Single.defer {
    Single.create<T> { emitter ->
        emitter canEmitt {
            object : Callback<T>() {
                override fun success(result: Result<T>) {
                    onSuccess(result.data)
                }

                override fun failure(exception: TwitterException) {
                    onError(exception)
                }

            }.let {
                enqueue(it)
                setCancellable { cancel() } //if we cancel the subscription the we cancel the request
            }
        }
    }
}