package com.nimble.nimblerewards.usecases.wallet

import com.nimble.nimblerewards.data.exceptions.AppError
import com.nimble.nimblerewards.data.exceptions.WalletError.ImportWalletError
import com.nimble.nimblerewards.data.repositories.WalletRepository
import com.nimble.nimblerewards.usecases.RxScheduler.IoThread
import com.nimble.nimblerewards.usecases.RxScheduler.MainThread
import com.nimble.nimblerewards.usecases.UseCase
import io.reactivex.Single
import javax.inject.Inject

class ImportWalletUseCase @Inject constructor(
    ioThread: IoThread,
    mainThread: MainThread,
    private val walletRepository: WalletRepository
) : UseCase<String, String>(ioThread, mainThread) {

    override fun create(privateKey: String): Single<String> {
        return walletRepository.importWallet(privateKey)
    }

    override fun convertError(throwable: Throwable): (Throwable) -> AppError {
        return ::ImportWalletError
    }
}
