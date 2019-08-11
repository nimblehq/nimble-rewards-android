package com.nimble.nimblerewards.config

import com.nimble.nimblerewards.BuildConfig

sealed class Environment {

    object Staging : Environment()
    object Production : Environment()

    val INFURA_RINKEBY_ENDPOINT: String
        get() = when (this) {
            is Staging, is Production -> "https://rinkeby.infura.io/v3/86d6db5bf52b4985a6d9531fd51cc04f"
        }

    val NIMBLE_GOLD_TOKEN_ADDRESS: String
        get() = when (this) {
            is Staging, is Production -> "0x9a47561cf2ce4326aa2523818d7902585a92c667"
        }

    val NIMBLE_REWARD_TOKEN_ADDRESS: String
        get() = when (this) {
            is Staging, is Production -> "0x363237203a6a36a443fcd7455d1f28d33244774b"
        }

    companion object {
        operator fun invoke(debug: Boolean = BuildConfig.DEBUG): Environment {
            return if (debug) Staging else Production
        }
    }
}
