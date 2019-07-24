package com.nimble.nimblerewards.usecases.transfer

import com.nimble.nimblerewards.data.exceptions.AppError
import com.nimble.nimblerewards.data.exceptions.EthereumError.TransferEthError
import com.nimble.nimblerewards.data.repositories.EthRepository
import com.nimble.nimblerewards.usecases.RxScheduler.*
import com.nimble.nimblerewards.usecases.UseCase
import com.nimble.nimblerewards.usecases.transfer.TransferEthUseCase.Params
import io.reactivex.Single
import org.web3j.protocol.core.methods.response.TransactionReceipt
import java.math.BigDecimal
import javax.inject.Inject

class TransferEthUseCase @Inject constructor(
    ioThread: IoThread,
    mainThread: MainThread,
    private val ethRepository: EthRepository
) : UseCase<TransactionReceipt, Params>(ioThread, mainThread) {

    data class Params(val amount: BigDecimal, val fromAddress: String, val toAddress: String)

    override fun create(params: Params): Single<TransactionReceipt> {
        return ethRepository.transferEth(params.amount, params.fromAddress, params.toAddress)
    }

    override fun convertError(throwable: Throwable): (Throwable) -> AppError {
        return ::TransferEthError
    }
}
