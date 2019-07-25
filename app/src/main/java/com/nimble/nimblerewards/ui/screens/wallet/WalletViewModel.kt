package com.nimble.nimblerewards.ui.screens.wallet

import android.app.Application
import com.nimble.nimblerewards.data.exceptions.WalletError.WalletDoesNotExistError
import com.nimble.nimblerewards.data.models.Wallet
import com.nimble.nimblerewards.ui.common.BaseViewModel
import com.nimble.nimblerewards.usecases.wallet.FetchEthBalanceUseCase
import com.nimble.nimblerewards.usecases.wallet.FetchNbgBalanceUseCase
import com.nimble.nimblerewards.usecases.wallet.FetchNbgSymbolUseCase
import com.nimble.nimblerewards.usecases.wallet.LoadWalletUseCase
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.Singles
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class WalletViewModel @Inject constructor(
    application: Application,
    private val loadWalletUseCase: LoadWalletUseCase,
    private val fetchEthBalanceUseCase: FetchEthBalanceUseCase,
    private val fetchNbgBalanceUseCase: FetchNbgBalanceUseCase,
    private val fetchNbgSymbolUseCase: FetchNbgSymbolUseCase
) : BaseViewModel(application) {

    private val _openSignIn = PublishSubject.create<Unit>()
    private val _wallet = BehaviorSubject.create<Wallet>()

    val openSignIn: Observable<Unit>
        get() = _openSignIn

    val wallet: Observable<Wallet>
        get() = _wallet

    fun loadWallet(): Completable {
        return loadWalletUseCase.execute(Unit)
            .flatMap {
                Singles.zip(
                    fetchEthBalanceUseCase.execute(it.address),
                    fetchNbgBalanceUseCase.execute(it.address),
                    fetchNbgSymbolUseCase.execute(it.address)
                ).map { (ethBalance, nbgBalance, nbgSymbol) ->
                    Wallet(
                        it.address,
                        ethBalance,
                        nbgBalance,
                        nbgSymbol,
                        ethBalance.plus(nbgBalance)
                    )
                }
            }
            .doOnError {
                if (it is WalletDoesNotExistError) {
                    _openSignIn.onNext(Unit)
                }
            }
            .doOnSuccess(_wallet::onNext)
            .ignoreElement()
    }
}
