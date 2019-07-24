package com.nimble.nimblerewards.data.exceptions

import androidx.annotation.StringRes
import com.nimble.nimblerewards.R

sealed class EthereumError(
    cause: Throwable?,
    @StringRes override val userMessage: Int?
) : AppError(cause, userMessage) {

    class FetchEthBalanceError(cause: Throwable?) : EthereumError(cause, R.string.error_fetch_eth_balance)
    class TransferEthError(cause: Throwable?) : EthereumError(cause, R.string.error_transfer_eth)
}
