package com.nimble.nimblerewards.di.components

import android.app.Application
import com.nimble.nimblerewards.AndroidApplication
import com.nimble.nimblerewards.di.modules.ActivityModule
import com.nimble.nimblerewards.di.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityModule::class
    ]
)
interface AppComponent : AndroidInjector<AndroidApplication> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}
