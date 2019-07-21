package com.nimble.nimblerewards.data.repositories

import com.nimble.nimblerewards.data.apis.EthereumApi
import com.nimble.nimblerewards.data.gateways.WalletHelper
import io.reactivex.Completable
import io.reactivex.Single
import java.math.BigDecimal
import javax.inject.Inject
import javax.inject.Singleton

interface EthRepository {
    fun fetchBalance(address: String): Single<BigDecimal>
    fun transferEth(amount: BigDecimal, from: String, to: String): Completable
}

@Singleton
class EthRepositoryImpl @Inject constructor(
    private val ethereumApi: EthereumApi,
    private val walletHelper: WalletHelper
) : EthRepository {

    override fun fetchBalance(address: String): Single<BigDecimal> {
        return ethereumApi.getBalance(address)
    }

    override fun transferEth(amount: BigDecimal, from: String, to: String): Completable {
        return Single.just(Unit)
            .map { walletHelper.loadCredentials(from) }
            .flatMapCompletable {
                ethereumApi.transferEth(amount, to, it)
            }
    }
}
