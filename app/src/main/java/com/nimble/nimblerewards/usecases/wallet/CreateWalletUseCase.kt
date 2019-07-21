package com.nimble.nimblerewards.usecases.wallet

import com.nimble.nimblerewards.data.exceptions.AppError
import com.nimble.nimblerewards.data.exceptions.WalletError.CreateWalletError
import com.nimble.nimblerewards.data.repositories.WalletRepository
import com.nimble.nimblerewards.usecases.RxScheduler.IoThread
import com.nimble.nimblerewards.usecases.RxScheduler.MainThread
import com.nimble.nimblerewards.usecases.UseCase
import io.reactivex.Single
import javax.inject.Inject

class CreateWalletUseCase @Inject constructor(
    ioThread: IoThread,
    mainThread: MainThread,
    private val walletRepository: WalletRepository
) : UseCase<String, Unit>(ioThread, mainThread) {

    override fun create(params: Unit): Single<String> {
        return walletRepository.generateWallet()
    }

    override fun convertError(throwable: Throwable): (Throwable) -> AppError {
        return ::CreateWalletError
    }
}
