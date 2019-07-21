package com.nimble.nimblerewards.config

import com.nimble.nimblerewards.BuildConfig

sealed class Environment {

    object Staging : Environment()
    object Production : Environment()

    val INFURA_RINKEBY_ENDPOINT: String
        get() = when (this) {
            is Staging, is Production -> "https://rinkeby.infura.io/v3/86d6db5bf52b4985a6d9531fd51cc04f"
        }

    companion object {
        operator fun invoke(debug: Boolean = BuildConfig.DEBUG): Environment {
            return if (debug) Staging else Production
        }
    }
}
