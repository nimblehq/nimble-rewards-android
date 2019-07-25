package com.nimble.nimblerewards.di.modules

import com.nimble.nimblerewards.data.apis.EthereumApi
import com.nimble.nimblerewards.data.apis.EthereumApiImpl
import com.nimble.nimblerewards.data.contract.NimbleGoldToken
import com.nimble.nimblerewards.data.contract.NimbleGoldTokenImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface EthereumModule {

    @Binds
    @Singleton
    fun ethereumApi(ethereumApi: EthereumApiImpl): EthereumApi

    @Binds
    @Singleton
    fun nimbleGoldToken(nimbleGoldToken: NimbleGoldTokenImpl): NimbleGoldToken
}
