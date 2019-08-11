package com.nimble.nimblerewards.di.modules

import com.nimble.nimblerewards.data.apis.*
import com.nimble.nimblerewards.data.contract.NimbleGoldToken
import com.nimble.nimblerewards.data.contract.NimbleGoldTokenImpl
import com.nimble.nimblerewards.data.contract.NimbleRewardToken
import com.nimble.nimblerewards.data.contract.NimbleRewardTokenImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface BlockChainModule {

    @Binds
    @Singleton
    fun ethereumApi(ethereumApi: EthereumApiImpl): EthereumApi

    @Binds
    @Singleton
    fun nimbleGoldApi(nimbleGoldApi: NimbleGoldApiImpl): NimbleGoldApi

    @Binds
    @Singleton
    fun nimbleRewardApi(nimbleRewardApi: NimbleRewardApiImpl): NimbleRewardApi

    @Binds
    @Singleton
    fun nimbleGoldToken(nimbleGoldToken: NimbleGoldTokenImpl): NimbleGoldToken

    @Binds
    @Singleton
    fun nimbleRewardToken(nimbleRewardToken: NimbleRewardTokenImpl): NimbleRewardToken
}
