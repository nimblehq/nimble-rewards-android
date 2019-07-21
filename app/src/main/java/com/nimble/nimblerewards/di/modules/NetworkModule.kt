package com.nimble.nimblerewards.di.modules

import com.nimble.nimblerewards.config.Environment
import com.nimble.nimblerewards.data.apis.EthereumApi
import com.nimble.nimblerewards.data.apis.EthereumApiImpl
import dagger.Module
import dagger.Provides
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import javax.inject.Singleton

@Module
internal class NetworkModule {

    @Provides
    @Singleton
    internal fun ethereumApi(web3j: Web3j, environment: Environment): EthereumApi = EthereumApiImpl(web3j, environment)

    @Provides
    @Singleton
    internal fun web3j(environment: Environment): Web3j = Web3j.build(HttpService(environment.INFURA_RINKEBY_ENDPOINT))
}
