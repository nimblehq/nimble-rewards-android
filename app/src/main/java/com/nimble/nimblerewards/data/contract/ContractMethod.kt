package com.nimble.nimblerewards.data.contract

import org.web3j.abi.FunctionEncoder
import org.web3j.abi.FunctionReturnDecoder
import org.web3j.abi.datatypes.Function
import org.web3j.protocol.core.methods.request.Transaction
import java.math.BigInteger

interface ContractMethod<T> {
    fun createTransaction(from: String): Transaction
    fun parseResponse(response: String): T
}

class ContractMethodImpl<T> constructor(
    private val contractAddress: String,
    private val function: Function
) : ContractMethod<T> {

    override fun createTransaction(from: String): Transaction {
        return Transaction(
            from,
            null,
            BigInteger.ZERO,
            null,
            contractAddress,
            BigInteger.ZERO,
            FunctionEncoder.encode(function)
        )
    }

    @Suppress("UNCHECKED_CAST")
    override fun parseResponse(response: String): T {
        return FunctionReturnDecoder
            .decode(response, function.outputParameters)
            .first()
            .value as T
    }
}
