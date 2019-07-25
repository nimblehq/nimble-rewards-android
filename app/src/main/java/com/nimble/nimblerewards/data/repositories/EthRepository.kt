package com.nimble.nimblerewards.data.repositories

import com.nimble.nimblerewards.data.apis.EthereumApi
import com.nimble.nimblerewards.data.gateways.WalletHelper
import io.reactivex.Single
import org.web3j.protocol.core.methods.response.TransactionReceipt
import java.math.BigDecimal
import javax.inject.Inject
import javax.inject.Singleton

interface EthRepository {
    fun fetchEthBalance(address: String): Single<BigDecimal>
    fun fetchNbgBalance(address: String): Single<BigDecimal>
    fun transferEth(amount: BigDecimal, from: String, to: String): Single<TransactionReceipt>
}

@Singleton
class EthRepositoryImpl @Inject constructor(
    private val ethereumApi: EthereumApi,
    private val walletHelper: WalletHelper
) : EthRepository {

    override fun fetchEthBalance(address: String): Single<BigDecimal> {
        return ethereumApi.getEthBalance(address)
    }

    override fun fetchNbgBalance(address: String): Single<BigDecimal> {
        return ethereumApi.getNbgBalance(address, address)
    }

    override fun transferEth(
        amount: BigDecimal,
        from: String,
        to: String
    ): Single<TransactionReceipt> {
        return Single.fromCallable {
            walletHelper.loadCredentials(from)
        }.flatMap {
            ethereumApi.transferEth(amount, to, it)
        }
    }
}
