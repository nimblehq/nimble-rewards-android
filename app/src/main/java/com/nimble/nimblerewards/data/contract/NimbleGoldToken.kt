package com.nimble.nimblerewards.data.contract

import com.nimble.nimblerewards.config.Environment
import com.nimble.nimblerewards.data.contract.NimbleGoldToken.Methods
import com.nimble.nimblerewards.extensions.toWeb3jAddress
import com.nimble.nimblerewards.extensions.uint256
import com.nimble.nimblerewards.extensions.utf8String
import org.web3j.abi.datatypes.Function
import java.math.BigInteger
import javax.inject.Inject
import javax.inject.Singleton

interface NimbleGoldToken : BaseContract {

    fun balanceOf(walletAddress: String): ContractMethod<BigInteger>

    fun decimals(): ContractMethod<Int>

    fun symbol(): ContractMethod<String>

    object Methods {
        const val balanceOf = "balanceOf"
        const val decimals = "decimals"
        const val symbol = "symbol"
    }
}

@Singleton
class NimbleGoldTokenImpl @Inject constructor(
    private val environment: Environment
) : NimbleGoldToken {

    override fun balanceOf(walletAddress: String): ContractMethod<BigInteger> {
        return ContractMethodImpl(
            environment.NIMBLE_GOLD_TOKEN_ADDRESS,
            Function(
                Methods.balanceOf,
                listOf(walletAddress.toWeb3jAddress()),
                listOf(uint256)
            )
        )
    }

    override fun decimals(): ContractMethod<Int> {
        return ContractMethodImpl(
            environment.NIMBLE_GOLD_TOKEN_ADDRESS,
            Function(
                Methods.decimals,
                emptyList(),
                listOf(uint256)
            )
        )
    }

    override fun symbol(): ContractMethod<String> {
        return ContractMethodImpl(
            environment.NIMBLE_GOLD_TOKEN_ADDRESS,
            Function(
                Methods.symbol,
                emptyList(),
                listOf(utf8String)
            )
        )
    }
}
