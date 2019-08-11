package com.nimble.nimblerewards.di.modules

import com.nimble.nimblerewards.data.repositories.BlockChainRepository
import com.nimble.nimblerewards.data.repositories.BlockChainRepositoryImpl
import com.nimble.nimblerewards.data.repositories.WalletRepository
import com.nimble.nimblerewards.data.repositories.WalletRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(includes = [GatewayModule::class])
interface RepositoryModule {
    @Binds
    @Singleton
    fun walletRepository(walletRepository: WalletRepositoryImpl): WalletRepository

    @Binds
    @Singleton
    fun blockChainRepository(blockChainRepository: BlockChainRepositoryImpl): BlockChainRepository
}
