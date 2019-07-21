package com.nimble.nimblerewards.di.modules

import com.nimble.nimblerewards.data.gateways.WalletHelper
import com.nimble.nimblerewards.data.gateways.WalletHelperImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface GatewayModule {
    @Binds
    @Singleton
    fun walletHelper(walletHelper: WalletHelperImpl): WalletHelper
}
