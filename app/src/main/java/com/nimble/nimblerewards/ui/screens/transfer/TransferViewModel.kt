package com.nimble.nimblerewards.ui.screens.transfer

import android.app.Application
import com.nimble.nimblerewards.ui.common.BaseViewModel
import com.nimble.nimblerewards.usecases.transfer.TransferEthUseCase
import com.nimble.nimblerewards.usecases.wallet.LoadWalletUseCase
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.rxkotlin.Singles
import io.reactivex.subjects.BehaviorSubject
import org.web3j.protocol.core.methods.response.TransactionReceipt
import java.math.BigDecimal
import javax.inject.Inject

class TransferViewModel @Inject constructor(
    application: Application,
    private val loadWalletUseCase: LoadWalletUseCase,
    private val transferEthUseCase: TransferEthUseCase
) : BaseViewModel(application) {

    private val _targetAddress = BehaviorSubject.create<String>()
    private val _amount = BehaviorSubject.create<BigDecimal>()

    fun transfer(): Single<TransactionReceipt> {
        return Singles.zip(
            loadWalletUseCase.execute(Unit).map { it.address },
            _targetAddress.firstOrError()
        ).flatMap { (fromAddress, toAddress) ->
            transferEthUseCase.execute(
                TransferEthUseCase.Params(
                    requireNotNull(_amount.value), fromAddress, toAddress
                )
            )
        }
    }

    fun verifyWalletAddressError(address: String): Completable {
        return Completable.fromCallable {
            if (address.isEmpty()) {
                throw IllegalArgumentException("Wallet Address is invalid")
            }
        }.doOnComplete { _targetAddress.onNext(address) }
    }

    fun verifyAmount(amount: String): Completable {
        return Completable.fromCallable {
            if (amount.isEmpty()) {
                throw IllegalArgumentException("Amount cannot be empty")
            }

            try {
                val bigAmount = amount.toBigDecimal()
                if (bigAmount <= BigDecimal.ZERO) {
                    throw IllegalArgumentException("Amount must be positive")
                }
            } catch (e: NumberFormatException) {
                throw IllegalArgumentException("Invalid amount number")
            }
        }.doOnComplete { _amount.onNext(amount.toBigDecimal()) }
    }
}
