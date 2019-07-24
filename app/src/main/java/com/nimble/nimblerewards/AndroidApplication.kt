package com.nimble.nimblerewards

import android.util.Log
import com.nimble.nimblerewards.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import java.io.IOException
import java.net.SocketException

class AndroidApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        registerRxGlobalErrorHandler()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }

    /**
     * Global handler for RxJava exceptions like UndeliverableException
     */
    private fun registerRxGlobalErrorHandler() {
        RxJavaPlugins.setErrorHandler {
            val e = if (it is UndeliverableException) {
                it.cause
            } else it

            if (e is IOException || e is SocketException) {
                // fine, irrelevant network problem or API that throws on cancellation
                return@setErrorHandler
            }
            if (e is InterruptedException) {
                // fine, some blocking code was interrupted by a dispose call
                return@setErrorHandler
            }
            if (e is NullPointerException || e is IllegalArgumentException) {
                // that's likely a bug in the application
                Thread.currentThread().uncaughtExceptionHandler?.uncaughtException(Thread.currentThread(), e)
                return@setErrorHandler
            }
            if (e is IllegalStateException) {
                // that's a bug in RxJava or in a custom operator
                Thread.currentThread().uncaughtExceptionHandler?.uncaughtException(Thread.currentThread(), e)
                return@setErrorHandler
            }
            Log.e(null, "Undeliverable exception received, not sure what to do", e)
        }
    }
}
