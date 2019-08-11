package com.nimble.nimblerewards.ui.screens.wallet

import android.app.Application
import com.nimble.nimblerewards.data.exceptions.WalletError.WalletDoesNotExistError
import com.nimble.nimblerewards.data.models.Wallet
import com.nimble.nimblerewards.ui.common.BaseViewModel
import com.nimble.nimblerewards.usecases.wallet.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.rxkotlin.Singles
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class WalletViewModel @Inject constructor(
    application: Application,
    private val loadWalletUseCase: LoadWalletUseCase,
    private val fetchEthBalanceUseCase: FetchEthBalanceUseCase,
    private val fetchNbgBalanceUseCase: FetchNbgBalanceUseCase,
    private val fetchNbgSymbolUseCase: FetchNbgSymbolUseCase,
    private val fetchNrwBalanceUseCase: FetchNrwBalanceUseCase,
    private val fetchNrwSymbolUseCase: FetchNrwSymbolUseCase
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
                    fetchNimbleGoldInfo(it.address),
                    fetchNimbleRewardInfo(it.address)
                ).map { (ethBalance, nbgInfo, nrwInfo) ->
                    Wallet(
                        it.address,
                        it.ecKeyPair.privateKey.toString(16),
                        ethBalance,
                        nbgInfo.first,
                        nbgInfo.second,
                        nrwInfo.first,
                        nrwInfo.second,
                        ethBalance.plus(nbgInfo.second).plus(nrwInfo.second)
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

    private fun fetchNimbleGoldInfo(address: String) =
        Singles.zip(
            fetchNbgSymbolUseCase.execute(address),
            fetchNbgBalanceUseCase.execute(address)
        )

    private fun fetchNimbleRewardInfo(address: String) =
        Singles.zip(
            fetchNrwSymbolUseCase.execute(address),
            fetchNrwBalanceUseCase.execute(address)
        )
}
