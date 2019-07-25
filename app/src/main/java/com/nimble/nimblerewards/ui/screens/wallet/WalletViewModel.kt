package com.nimble.nimblerewards.ui.screens.wallet

import android.app.Application
import com.nimble.nimblerewards.data.exceptions.WalletError.WalletDoesNotExistError
import com.nimble.nimblerewards.data.models.Wallet
import com.nimble.nimblerewards.ui.common.BaseViewModel
import com.nimble.nimblerewards.usecases.wallet.FetchEthBalanceUseCase
import com.nimble.nimblerewards.usecases.wallet.FetchNbgBalanceUseCase
import com.nimble.nimblerewards.usecases.wallet.LoadWalletUseCase
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.Singles
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class WalletViewModel @Inject constructor(
    application: Application,
    private val loadWalletUseCase: LoadWalletUseCase,
    private val fetchEthBalanceUseCase: FetchEthBalanceUseCase,
    private val fetchNbgBalanceUseCase: FetchNbgBalanceUseCase
) : BaseViewModel(application) {

    private val _openSignIn = PublishSubject.create<Unit>()

    val openSignIn: Observable<Unit>
        get() = _openSignIn

    fun loadWallet(): Single<Wallet> {
        return loadWalletUseCase.execute(Unit)
            .flatMap {
                Singles.zip(
                    fetchEthBalanceUseCase.execute(it.address),
                    fetchNbgBalanceUseCase.execute(it.address)
                ).map { (ethBalance, nbgBalance) ->
                    Wallet(it.address, ethBalance, nbgBalance)
                }
            }
            .doOnError {
                if (it is WalletDoesNotExistError) {
                    _openSignIn.onNext(Unit)
                }
            }
    }
}
