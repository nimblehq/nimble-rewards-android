package com.nimble.nimblerewards.ui.customviews

import android.content.Context
import android.widget.Toast
import com.nimble.nimblerewards.data.exceptions.userReadableMessage
import javax.inject.Inject
import javax.inject.Singleton

interface Toaster {
    fun display(message: String)
    fun display(throwable: Throwable)
}

@Singleton
class ToasterImpl @Inject constructor(
    private val context: Context
) : Toaster {

    private var toast: Toast? = null

    override fun display(message: String) {
        toast?.cancel()
        toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
            .apply { show() }
    }

    override fun display(throwable: Throwable) {
        val cause = throwable.message
        val userReadableMessage = throwable.userReadableMessage(context)
        display("$userReadableMessage $cause")
    }
}
