package com.nimble.nimblerewards.data.apis

import com.nimble.nimblerewards.data.contract.NimbleRewardToken
import io.reactivex.Single
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import java.math.BigDecimal
import javax.inject.Inject

interface NimbleRewardApi {
    fun getNrwBalance(callerAddress: String, walletAddress: String): Single<BigDecimal>
    fun getNrwSymbol(callerAddress: String): Single<String>
}

class NimbleRewardApiImpl @Inject constructor(
    private val web3j: Web3j,
    private val nimbleRewardToken: NimbleRewardToken
) : NimbleRewardApi {

    override fun getNrwBalance(callerAddress: String, walletAddress: String): Single<BigDecimal> {
        val method = nimbleRewardToken.balanceOf(walletAddress)
        return web3j.ethCall(
            method.createTransaction(callerAddress),
            DefaultBlockParameterName.LATEST
        )
            .flowable()
            .firstOrError()
            .map { method.parseResponse(it.value).toBigDecimal() }
    }

    override fun getNrwSymbol(callerAddress: String): Single<String> {
        val method = nimbleRewardToken.symbol()
        return web3j.ethCall(
            method.createTransaction(callerAddress),
            DefaultBlockParameterName.LATEST
        )
            .flowable()
            .firstOrError()
            .map { method.parseResponse(it.value) }
    }
}
