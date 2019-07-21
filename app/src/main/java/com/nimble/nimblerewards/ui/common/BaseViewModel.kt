package com.nimble.nimblerewards.ui.common

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import com.nimble.nimblerewards.AndroidApplication
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    private val disposables by lazy { CompositeDisposable() }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    protected fun Disposable.bindForDisposable() {
        disposables.add(this)
    }

    fun getString(@StringRes resId: Int): String =
        getApplication<AndroidApplication>().getString(resId)

    fun getString(@StringRes resId: Int, vararg params: Any): String =
        getApplication<AndroidApplication>().getString(resId, *params)
}
