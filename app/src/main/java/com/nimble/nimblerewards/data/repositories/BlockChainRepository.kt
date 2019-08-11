package com.nimble.nimblerewards.data.repositories

import com.nimble.nimblerewards.data.apis.EthereumApi
import com.nimble.nimblerewards.data.apis.NimbleGoldApi
import com.nimble.nimblerewards.data.apis.NimbleRewardApi
import com.nimble.nimblerewards.data.gateways.WalletHelper
import io.reactivex.Single
import org.web3j.protocol.core.methods.response.TransactionReceipt
import java.math.BigDecimal
import javax.inject.Inject
import javax.inject.Singleton

interface BlockChainRepository {
    fun fetchEthBalance(address: String): Single<BigDecimal>
    fun fetchNbgBalance(address: String): Single<BigDecimal>
    fun fetchNrwBalance(address: String): Single<BigDecimal>

    fun fetchNbgSymbol(address: String): Single<String>
    fun fetchNrwSymbol(address: String): Single<String>

    fun transferEth(amount: BigDecimal, from: String, to: String): Single<TransactionReceipt>
}

@Singleton
class BlockChainRepositoryImpl @Inject constructor(
    private val ethereumApi: EthereumApi,
    private val nimbleGoldApi: NimbleGoldApi,
    private val nimbleRewardApi: NimbleRewardApi,
    private val walletHelper: WalletHelper
) : BlockChainRepository {

    override fun fetchEthBalance(address: String): Single<BigDecimal> {
        return ethereumApi.getEthBalance(address)
    }

    override fun fetchNbgBalance(address: String): Single<BigDecimal> {
        return nimbleGoldApi.getNbgBalance(address, address)
    }

    override fun fetchNrwBalance(address: String): Single<BigDecimal> {
        return nimbleRewardApi.getNrwBalance(address, address)
    }

    override fun fetchNbgSymbol(address: String): Single<String> {
        return nimbleGoldApi.getNbgSymbol(address)
    }

    override fun fetchNrwSymbol(address: String): Single<String> {
        return nimbleRewardApi.getNrwSymbol(address)
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
