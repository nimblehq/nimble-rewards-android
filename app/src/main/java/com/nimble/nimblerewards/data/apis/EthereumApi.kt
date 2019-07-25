package com.nimble.nimblerewards.data.apis

import com.nimble.nimblerewards.data.contract.NimbleGoldToken
import io.reactivex.Single
import io.reactivex.rxkotlin.Singles
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.response.EthGetBalance
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.tx.Transfer
import org.web3j.utils.Convert
import java.math.BigDecimal
import javax.inject.Inject

interface EthereumApi {
    fun getEthBalance(callerAddress: String): Single<BigDecimal>
    fun getNbgBalance(callerAddress: String, walletAddress: String): Single<BigDecimal>
    fun getNbgDecimals(callerAddress: String): Single<Int>

    fun transferEth(
        amount: BigDecimal,
        to: String,
        credentials: Credentials
    ): Single<TransactionReceipt>
}

class EthereumApiImpl @Inject constructor(
    private val web3j: Web3j,
    private val nimbleGoldToken: NimbleGoldToken
) : EthereumApi {

    override fun getEthBalance(callerAddress: String): Single<BigDecimal> {
        return web3j.ethGetBalance(callerAddress, DefaultBlockParameterName.LATEST)
            .flowable()
            .firstOrError()
            .map { it.ethAmount }
    }

    override fun getNbgBalance(callerAddress: String, walletAddress: String): Single<BigDecimal> {
        val balanceOf = nimbleGoldToken.balanceOf(walletAddress)
        val decimals = nimbleGoldToken.decimals()

        return Singles.zip(
            web3j.ethCall(
                balanceOf.createTransaction(callerAddress),
                DefaultBlockParameterName.LATEST
            ).flowable().firstOrError(),
            web3j.ethCall(
                decimals.createTransaction(callerAddress),
                DefaultBlockParameterName.LATEST
            ).flowable().firstOrError()
        ).map {
            val balance = balanceOf.parseResponse(it.first.value).toBigDecimal()
            val decimals = decimals.parseResponse(it.second.value)
            balance.divide(BigDecimal.TEN.pow(decimals))
        }
    }

    override fun getNbgDecimals(callerAddress: String): Single<Int> {
        val method = nimbleGoldToken.decimals()
        return web3j.ethCall(
            method.createTransaction(callerAddress),
            DefaultBlockParameterName.LATEST
        )
            .flowable()
            .firstOrError()
            .map { method.parseResponse(it.value) }
    }

    override fun transferEth(
        amount: BigDecimal,
        to: String,
        credentials: Credentials
    ): Single<TransactionReceipt> {
        return Transfer.sendFunds(web3j, credentials, to, amount, Convert.Unit.ETHER)
            .flowable()
            .firstOrError()
    }

    private val EthGetBalance.ethAmount: BigDecimal
        get() = Convert.fromWei(balance.toBigDecimal(), Convert.Unit.ETHER)
}
