package com.nimble.nimblerewards.usecases.wallet

import com.nimble.nimblerewards.data.exceptions.AppError
import com.nimble.nimblerewards.data.exceptions.EthereumError.FetchEthBalanceError
import com.nimble.nimblerewards.data.repositories.BlockChainRepository
import com.nimble.nimblerewards.usecases.RxScheduler.IoThread
import com.nimble.nimblerewards.usecases.RxScheduler.MainThread
import com.nimble.nimblerewards.usecases.UseCase
import io.reactivex.Single
import java.math.BigDecimal
import javax.inject.Inject

class FetchEthBalanceUseCase @Inject constructor(
    ioThread: IoThread,
    mainThread: MainThread,
    private val blockChainRepository: BlockChainRepository
) : UseCase<BigDecimal, String>(ioThread, mainThread) {

    override fun create(walletAddress: String): Single<BigDecimal> {
        return blockChainRepository.fetchEthBalance(walletAddress)
    }

    override fun convertError(throwable: Throwable): (Throwable) -> AppError {
        return ::FetchEthBalanceError
    }
}
