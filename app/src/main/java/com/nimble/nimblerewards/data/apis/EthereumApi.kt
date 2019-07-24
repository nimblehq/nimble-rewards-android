package com.nimble.nimblerewards.data.apis

import com.nimble.nimblerewards.config.Environment
import io.reactivex.Single
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
    fun getBalance(address: String): Single<BigDecimal>
    fun transferEth(
        amount: BigDecimal,
        to: String,
        credentials: Credentials
    ): Single<TransactionReceipt>
}

class EthereumApiImpl @Inject constructor(
    private val web3j: Web3j,
    private val environment: Environment
) : EthereumApi {

    override fun getBalance(address: String): Single<BigDecimal> {
        return web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST)
            .flowable()
            .firstOrError()
            .map { it.ethAmount }
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
