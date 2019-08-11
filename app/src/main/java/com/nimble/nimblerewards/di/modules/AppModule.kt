package com.nimble.nimblerewards.di.modules

import android.app.Application
import android.content.Context
import com.nimble.nimblerewards.config.Environment
import com.nimble.nimblerewards.ui.customviews.Toaster
import com.nimble.nimblerewards.ui.customviews.ToasterImpl
import com.nimble.nimblerewards.usecases.RxScheduler
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [
        NetworkModule::class,
        BlockChainModule::class,
        ViewModelModule::class,
        RepositoryModule::class
    ]
)
abstract class AppModule {

    @Binds
    @Singleton
    internal abstract fun applicationContext(application: Application): Context

    @Binds
    @Singleton
    internal abstract fun toaster(toaster: ToasterImpl): Toaster

    @Module
    companion object {

        @JvmStatic
        @Provides
        @Singleton
        fun environment(): Environment = Environment()

        @JvmStatic
        @Provides
        @Singleton
        fun ioThread(): RxScheduler.IoThread = RxScheduler.IoThread

        @JvmStatic
        @Provides
        @Singleton
        fun mainThread(): RxScheduler.MainThread = RxScheduler.MainThread
    }
}
