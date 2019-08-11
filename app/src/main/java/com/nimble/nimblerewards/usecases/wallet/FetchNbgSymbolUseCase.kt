package com.nimble.nimblerewards.usecases.wallet

import com.nimble.nimblerewards.data.exceptions.AppError
import com.nimble.nimblerewards.data.exceptions.NimbleGoldError.FetchNbgSymbolError
import com.nimble.nimblerewards.data.repositories.BlockChainRepository
import com.nimble.nimblerewards.usecases.RxScheduler.IoThread
import com.nimble.nimblerewards.usecases.RxScheduler.MainThread
import com.nimble.nimblerewards.usecases.UseCase
import io.reactivex.Single
import javax.inject.Inject

class FetchNbgSymbolUseCase @Inject constructor(
    ioThread: IoThread,
    mainThread: MainThread,
    private val blockChainRepository: BlockChainRepository
) : UseCase<String, String>(ioThread, mainThread) {

    override fun create(address: String): Single<String> {
        return blockChainRepository.fetchNbgSymbol(address)
    }

    override fun convertError(throwable: Throwable): (Throwable) -> AppError {
        return ::FetchNbgSymbolError
    }
}
