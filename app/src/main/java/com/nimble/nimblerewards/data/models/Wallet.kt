package com.nimble.nimblerewards.data.models

import java.math.BigDecimal

data class Wallet(
    val address: String,
    val ethBalance: BigDecimal
)