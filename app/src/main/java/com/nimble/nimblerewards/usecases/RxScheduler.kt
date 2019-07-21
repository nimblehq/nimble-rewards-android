package com.nimble.nimblerewards.usecases

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

sealed class RxScheduler {
    object MainThread : RxScheduler()
    object IoThread : RxScheduler()

    fun get(): Scheduler {
        return when (this) {
            is MainThread -> AndroidSchedulers.mainThread()
            is IoThread -> Schedulers.io()
        }
    }
}
