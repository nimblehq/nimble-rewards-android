package com.nimble.nimblerewards.ui.screens.wallet

import android.app.Application
import com.nimble.nimblerewards.data.exceptions.WalletError.WalletDoesNotExistError
import com.nimble.nimblerewards.data.models.Wallet
import com.nimble.nimblerewards.ui.common.BaseViewModel
import com.nimble.nimblerewards.usecases.wallet.FetchEthBalanceUseCase
import com.nimble.nimblerewards.usecases.wallet.LoadWalletUseCase
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class WalletViewModel @Inject constructor(
    application: Application,
    private val loadWalletUseCase: LoadWalletUseCase,
    private val fetchEthBalanceUseCase: FetchEthBalanceUseCase
) : BaseViewModel(application) {

    private val _openSignIn = PublishSubject.create<Unit>()

    val openSignIn: Observable<Unit>
        get() = _openSignIn

    fun loadWallet(): Single<Wallet> {
        return loadWalletUseCase.execute(Unit)
            .flatMap {
                fetchEthBalanceUseCase
                    .execute(it.address)
                    .map { ethBalance -> it.address to ethBalance }
            }
            .map { Wallet(it.first, it.second) }
            .doOnError {
                if (it is WalletDoesNotExistError) {
                    _openSignIn.onNext(Unit)
                }
            }
    }
}
