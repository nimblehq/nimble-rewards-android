package com.nimble.nimblerewards.ui.screens.signin

import android.app.Application
import com.nimble.nimblerewards.ui.common.BaseViewModel
import com.nimble.nimblerewards.usecases.wallet.CreateWalletUseCase
import com.nimble.nimblerewards.usecases.wallet.ImportWalletUseCase
import io.reactivex.Single
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    application: Application,
    private val createWalletUseCase: CreateWalletUseCase,
    private val importWalletUseCase: ImportWalletUseCase
) : BaseViewModel(application) {

    fun createWallet(): Single<String> {
        return createWalletUseCase.execute(Unit)
    }

    fun importWallet(privateKey: String): Single<String> {
        return importWalletUseCase.execute(privateKey)
    }
}
