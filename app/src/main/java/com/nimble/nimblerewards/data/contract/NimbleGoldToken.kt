package com.nimble.nimblerewards.data.contract

import com.nimble.nimblerewards.config.Environment
import com.nimble.nimblerewards.data.contract.NimbleGoldToken.Methods
import com.nimble.nimblerewards.extensions.toWeb3jAddress
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.generated.Uint256
import java.math.BigInteger
import javax.inject.Inject
import javax.inject.Singleton

interface NimbleGoldToken : BaseContract {

    fun balanceOf(walletAddress: String): ContractMethod<BigInteger>

    fun decimals(): ContractMethod<Int>

    object Methods {
        const val balanceOf = "balanceOf"
        const val decimals = "decimals"
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

    private val uint256: TypeReference<Uint256>
        get() = TypeReference.create(Uint256::class.java)
}