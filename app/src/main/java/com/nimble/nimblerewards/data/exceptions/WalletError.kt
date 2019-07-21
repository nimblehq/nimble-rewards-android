package com.nimble.nimblerewards.data.exceptions

import androidx.annotation.StringRes
import com.nimble.nimblerewards.R

sealed class WalletError(
    cause: Throwable?,
    @StringRes override val userMessage: Int?
) : AppError(cause, userMessage) {

    class WalletDoesNotExistError(cause: Throwable?) : WalletError(cause, R.string.error_wallet_does_not_exist)
    class LoadWalletError(cause: Throwable?) : WalletError(cause, R.string.error_load_wallet)
    class CreateWalletError(cause: Throwable?) : WalletError(cause, R.string.error_create_wallet)
    class ImportWalletError(cause: Throwable?) : WalletError(cause, R.string.error_import_wallet)
}
