package com.nimble.nimblerewards.usecases.wallet

import com.nimble.nimblerewards.data.exceptions.AppError
import com.nimble.nimblerewards.data.exceptions.WalletError.LoadWalletError
import com.nimble.nimblerewards.data.exceptions.WalletError.WalletDoesNotExistError
import com.nimble.nimblerewards.data.repositories.WalletRepository
import com.nimble.nimblerewards.usecases.RxScheduler
import com.nimble.nimblerewards.usecases.UseCase
import io.reactivex.Single
import org.web3j.crypto.Credentials
import javax.inject.Inject

class LoadWalletUseCase @Inject constructor(
    ioThread: RxScheduler.IoThread,
    mainThread: RxScheduler.MainThread,
    private val walletRepository: WalletRepository
) : UseCase<Credentials, Unit>(ioThread, mainThread) {

    override fun create(params: Unit): Single<Credentials> {
        return walletRepository
            .loadWalletsCredentials()
            .filter { it.isNotEmpty() }
            .map { it.first() }
            .toSingle()
    }

    override fun convertError(throwable: Throwable): (Throwable) -> AppError {
        return when (throwable) {
            is NoSuchElementException -> ::WalletDoesNotExistError
            else -> ::LoadWalletError
        }
    }
}
