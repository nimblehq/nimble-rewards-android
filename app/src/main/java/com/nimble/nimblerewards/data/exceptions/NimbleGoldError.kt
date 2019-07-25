package com.nimble.nimblerewards.data.exceptions

import androidx.annotation.StringRes
import com.nimble.nimblerewards.R

sealed class NimbleGoldError(
    cause: Throwable?,
    @StringRes override val userMessage: Int?
) : AppError(cause, userMessage) {

    class FetchNbgBalanceError(cause: Throwable?) :
        NimbleGoldError(cause, R.string.error_fetch_nbg_balance)
    class FetchNbgSymbolError(cause: Throwable?) :
        NimbleGoldError(cause, R.string.error_fetch_nbg_symbol)
}
