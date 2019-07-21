package com.nimble.nimblerewards.ui.screens.settings

import android.app.Application
import com.nimble.nimblerewards.ui.common.BaseViewModel
import com.nimble.nimblerewards.usecases.signout.SignOutUseCase
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    application: Application,
    private val signOutUseCase: SignOutUseCase
) : BaseViewModel(application) {

    private val _openSignIn = PublishSubject.create<Unit>()

    val openSignIn: Observable<Unit>
        get() = _openSignIn

    fun signOut(): Completable {
        return signOutUseCase.execute(Unit)
            .doOnSuccess { _openSignIn.onNext(Unit) }
            .ignoreElement()
    }
}
