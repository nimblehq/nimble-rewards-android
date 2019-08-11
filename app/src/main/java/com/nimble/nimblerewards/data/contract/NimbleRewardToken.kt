package com.nimble.nimblerewards.data.contract

import com.nimble.nimblerewards.config.Environment
import com.nimble.nimblerewards.data.contract.NimbleRewardToken.Methods
import com.nimble.nimblerewards.extensions.toWeb3jAddress
import com.nimble.nimblerewards.extensions.uint256
import com.nimble.nimblerewards.extensions.utf8String
import org.web3j.abi.datatypes.Function
import java.math.BigInteger
import javax.inject.Inject
import javax.inject.Singleton

interface NimbleRewardToken : BaseContract {

    fun balanceOf(walletAddress: String): ContractMethod<BigInteger>

    fun symbol(): ContractMethod<String>

    object Methods {
        const val balanceOf = "balanceOf"
        const val symbol = "symbol"
    }
}

@Singleton
class NimbleRewardTokenImpl @Inject constructor(
    private val environment: Environment
) : NimbleRewardToken {

    override fun balanceOf(walletAddress: String): ContractMethod<BigInteger> {
        return ContractMethodImpl(
            environment.NIMBLE_REWARD_TOKEN_ADDRESS,
            Function(
                Methods.balanceOf,
                listOf(walletAddress.toWeb3jAddress()),
                listOf(uint256)
            )
        )
    }

    override fun symbol(): ContractMethod<String> {
        return ContractMethodImpl(
            environment.NIMBLE_REWARD_TOKEN_ADDRESS,
            Function(
                Methods.symbol,
                emptyList(),
                listOf(utf8String)
            )
        )
    }
}
