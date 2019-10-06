package com.example.modular.domain.core

import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.SingleTransformer
import kotlinx.coroutines.*

class AsyncTransform<T>(
    private val scope: CoroutineScope = GlobalScope,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : SingleTransformer<T,Deferred<T>> {

    override fun apply(upstream: Single<T>) : SingleSource<Deferred<T>> {
        return Single.fromCallable {
            scope.async(dispatcher) {
                upstream.blockingGet()
            }
        }
    }
}
