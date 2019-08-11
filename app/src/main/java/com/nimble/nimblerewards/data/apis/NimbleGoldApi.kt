package com.nimble.nimblerewards.data.apis

import com.nimble.nimblerewards.data.contract.NimbleGoldToken
import io.reactivex.Single
import io.reactivex.rxkotlin.Singles
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import java.math.BigDecimal
import javax.inject.Inject

interface NimbleGoldApi {
    fun getNbgBalance(callerAddress: String, walletAddress: String): Single<BigDecimal>
    fun getNbgDecimals(callerAddress: String): Single<Int>
    fun getNbgSymbol(callerAddress: String): Single<String>
}

class NimbleGoldApiImpl @Inject constructor(
    private val web3j: Web3j,
    private val nimbleGoldToken: NimbleGoldToken
) : NimbleGoldApi {

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
            .map { method.parseResponse(it.value).toBigDecimal() }
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
}
