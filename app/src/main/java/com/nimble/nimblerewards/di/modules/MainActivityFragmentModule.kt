package com.nimble.nimblerewards.di.modules

import com.nimble.nimblerewards.ui.screens.rewards.RewardsFragment
import com.nimble.nimblerewards.ui.screens.settings.SettingsFragment
import com.nimble.nimblerewards.ui.screens.wallet.WalletFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainActivityFragmentModule {

    @ContributesAndroidInjector
    fun rewardsFragment(): RewardsFragment

    @ContributesAndroidInjector
    fun settingsFragment(): SettingsFragment

    @ContributesAndroidInjector
    fun walletFragment(): WalletFragment
}
