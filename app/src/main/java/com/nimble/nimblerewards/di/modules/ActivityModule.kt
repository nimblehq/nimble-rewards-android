package com.nimble.nimblerewards.di.modules

import com.nimble.nimblerewards.ui.screens.MainActivity
import com.nimble.nimblerewards.ui.screens.signin.SignInActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityModule {

    @ContributesAndroidInjector(modules = [MainActivityFragmentModule::class])
    fun mainActivity(): MainActivity

    @ContributesAndroidInjector
    fun signInActivity(): SignInActivity
}
