package com.nimble.nimblerewards.usecases.wallet

import com.nimble.nimblerewards.data.exceptions.AppError
import com.nimble.nimblerewards.data.exceptions.NimbleGoldError.FetchNbgBalanceError
import com.nimble.nimblerewards.data.repositories.EthRepository
import com.nimble.nimblerewards.usecases.RxScheduler.IoThread
import com.nimble.nimblerewards.usecases.RxScheduler.MainThread
import com.nimble.nimblerewards.usecases.UseCase
import io.reactivex.Single
import java.math.BigDecimal
import javax.inject.Inject

class FetchNbgBalanceUseCase @Inject constructor(
    ioThread: IoThread,
    mainThread: MainThread,
    private val ethRepository: EthRepository
) : UseCase<BigDecimal, String>(ioThread, mainThread) {

    override fun create(address: String): Single<BigDecimal> {
        return ethRepository.fetchNbgBalance(address)
    }

    override fun convertError(throwable: Throwable): (Throwable) -> AppError {
        return ::FetchNbgBalanceError
    }
}
