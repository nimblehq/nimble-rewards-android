package com.nimble.nimblerewards.di.modules

import com.nimble.nimblerewards.config.Environment
import dagger.Module
import dagger.Provides
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import javax.inject.Singleton

@Module
internal class NetworkModule {

    @Provides
    @Singleton
    internal fun web3j(environment: Environment): Web3j =
        Web3j.build(HttpService(environment.INFURA_RINKEBY_ENDPOINT))
}
