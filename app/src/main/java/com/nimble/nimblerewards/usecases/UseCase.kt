package com.nimble.nimblerewards.usecases

import com.nimble.nimblerewards.data.exceptions.AppError
import io.reactivex.Single

abstract class UseCase<T, in Params>(
    private val executionThread: RxScheduler,
    private val postExecutionThread: RxScheduler
) {

    protected abstract fun create(params: Params): Single<T>

    protected abstract fun convertError(throwable: Throwable): (Throwable) -> AppError

    fun execute(params: Params): Single<T> = create(params)
        .onErrorResumeNext { Single.error<T>(convertError(it)(it)) }
        .doOnError(::doOnError)
        .subscribeOn(executionThread.get())
        .observeOn(postExecutionThread.get())

    private fun doOnError(throwable: Throwable) {
        // Crashlytics.log(throwable)
        throwable.printStackTrace()
    }
}
