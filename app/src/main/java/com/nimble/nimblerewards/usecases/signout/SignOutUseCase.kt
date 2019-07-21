package com.nimble.nimblerewards.usecases.signout

import com.nimble.nimblerewards.data.exceptions.AccountError.SignOutError
import com.nimble.nimblerewards.data.exceptions.AppError
import com.nimble.nimblerewards.data.repositories.WalletRepository
import com.nimble.nimblerewards.usecases.RxScheduler.IoThread
import com.nimble.nimblerewards.usecases.RxScheduler.MainThread
import com.nimble.nimblerewards.usecases.UseCase
import io.reactivex.Single
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    ioThread: IoThread,
    mainThread: MainThread,
    private val walletRepository: WalletRepository
) : UseCase<Unit, Unit>(ioThread, mainThread) {

    override fun create(params: Unit): Single<Unit> {
        return walletRepository.deleteWallets()
            .toSingleDefault(Unit)
    }

    override fun convertError(throwable: Throwable): (Throwable) -> AppError {
        return ::SignOutError
    }
}
