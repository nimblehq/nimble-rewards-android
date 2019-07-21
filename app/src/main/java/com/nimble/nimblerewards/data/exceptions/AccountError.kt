package com.nimble.nimblerewards.data.exceptions

import androidx.annotation.StringRes
import com.nimble.nimblerewards.R

sealed class AccountError(
    cause: Throwable?,
    @StringRes override val userMessage: Int?
) : AppError(cause, userMessage) {

    class SignOutError(cause: Throwable?) : AccountError(cause, R.string.error_sign_out)
}
