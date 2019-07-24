package com.nimble.nimblerewards.di.modules

import androidx.lifecycle.ViewModel
import com.nimble.nimblerewards.di.ViewModelKey
import com.nimble.nimblerewards.ui.screens.rewards.RewardsViewModel
import com.nimble.nimblerewards.ui.screens.settings.SettingsViewModel
import com.nimble.nimblerewards.ui.screens.signin.SignInViewModel
import com.nimble.nimblerewards.ui.screens.transfer.TransferViewModel
import com.nimble.nimblerewards.ui.screens.wallet.WalletViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(RewardsViewModel::class)
    fun rewardViewModel(viewModel: RewardsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    fun settingsViewModel(viewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WalletViewModel::class)
    fun walletViewModel(viewModel: WalletViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    fun signInViewModel(viewModel: SignInViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TransferViewModel::class)
    fun transferViewModel(viewModel: TransferViewModel): ViewModel
}
