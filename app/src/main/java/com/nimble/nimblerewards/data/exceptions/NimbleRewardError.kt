package com.nimble.nimblerewards.data.exceptions

import androidx.annotation.StringRes
import com.nimble.nimblerewards.R

sealed class NimbleRewardError(
    cause: Throwable?,
    @StringRes override val userMessage: Int?
) : AppError(cause, userMessage) {

    class FetchNrwBalanceError(cause: Throwable?) :
        NimbleRewardError(cause, R.string.error_fetch_nrw_balance)
    class FetchNrwSymbolError(cause: Throwable?) :
        NimbleRewardError(cause, R.string.error_fetch_nrw_symbol)
}
