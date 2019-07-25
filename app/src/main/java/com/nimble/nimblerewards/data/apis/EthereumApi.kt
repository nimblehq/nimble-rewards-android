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
    fun getNbgSymbol(callerAddress: String): Single<String>

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
        return Singles.zip(
            getNbgBalanceWithoutDecimals(callerAddress, walletAddress),
            getNbgDecimals(callerAddress)
        ).map { (balance, decimals) ->
            balance.divide(BigDecimal.TEN.pow(decimals))
        }
    }

    private fun getNbgBalanceWithoutDecimals(
        callerAddress: String,
        walletAddress: String
    ): Single<BigDecimal> {
        val method = nimbleGoldToken.balanceOf(walletAddress)
        return web3j.ethCall(
            method.createTransaction(callerAddress),
            DefaultBlockParameterName.LATEST
        )
            .flowable()
            .firstOrError()
            .map {
                method.parseResponse(it.value).toBigDecimal()
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

    override fun getNbgSymbol(callerAddress: String): Single<String> {
        val method = nimbleGoldToken.symbol()
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
