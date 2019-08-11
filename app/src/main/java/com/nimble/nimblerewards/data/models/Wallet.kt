package com.nimble.nimblerewards.data.models

import java.math.BigDecimal

data class Wallet(
    val address: String,
    val recoveryKey: String,
    val ethBalance: BigDecimal,
    val nbgSymbol: String,
    val nbgBalance: BigDecimal,
    val nrwSymbol: String,
    val nrwBalance: BigDecimal,
    val totalBalanceInUsd: BigDecimal
)
