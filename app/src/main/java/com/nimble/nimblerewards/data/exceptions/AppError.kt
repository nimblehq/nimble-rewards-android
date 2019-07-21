package com.nimble.nimblerewards.data.exceptions

import android.content.Context
import androidx.annotation.StringRes
import com.nimble.nimblerewards.R

open class AppError(
    cause: Throwable?,
    @StringRes open val userMessage: Int? = null
) : Throwable(cause)

fun Throwable.userReadableMessage(context: Context): String {
    return when (this) {
        is AppError -> context.getString(userMessage ?: R.string.error_generic)
        else -> context.getString(R.string.error_generic)
    }
}
