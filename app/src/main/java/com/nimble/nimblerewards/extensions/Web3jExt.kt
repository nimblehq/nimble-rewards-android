package com.nimble.nimblerewards.extensions

import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Utf8String
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.abi.datatypes.Address as Web3jAddress

fun String.toWeb3jAddress() = Web3jAddress(this)

val uint256: TypeReference<Uint256>
    get() = TypeReference.create(Uint256::class.java)

val utf8String: TypeReference<Utf8String>
    get() = TypeReference.create(Utf8String::class.java)
