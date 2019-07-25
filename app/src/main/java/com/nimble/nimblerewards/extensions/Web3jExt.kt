package com.nimble.nimblerewards.extensions

import org.web3j.abi.datatypes.Address as Web3jAddress

fun String.toWeb3jAddress() = Web3jAddress(this)
