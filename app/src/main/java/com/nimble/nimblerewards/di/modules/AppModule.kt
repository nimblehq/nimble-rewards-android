package com.nimble.nimblerewards.di.modules

import android.app.Application
import android.content.Context
import com.nimble.nimblerewards.config.Environment
import com.nimble.nimblerewards.usecases.RxScheduler
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, ViewModelModule::class, RepositoryModule::class])
internal class AppModule {
    @Provides
    @Singleton
    internal fun environment(): Environment = Environment()

    @Provides
    @Singleton
    internal fun ioThread(): RxScheduler.IoThread = RxScheduler.IoThread

    @Provides
    @Singleton
    internal fun mainThread(): RxScheduler.MainThread = RxScheduler.MainThread

    @Provides
    @Singleton
    internal fun applicationContext(application: Application): Context = application
}
