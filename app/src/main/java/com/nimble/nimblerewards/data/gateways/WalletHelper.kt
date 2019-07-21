package com.nimble.nimblerewards.data.gateways

import android.content.Context
import com.nimble.nimblerewards.data.gateways.WalletHelper.Companion.WALLET_FILE_PASSWORD
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.web3j.crypto.Credentials
import org.web3j.crypto.ECKeyPair
import org.web3j.crypto.WalletUtils
import java.io.File
import java.math.BigInteger
import java.security.Security
import javax.inject.Inject
import javax.inject.Singleton

interface WalletHelper {
    fun generateLightNewWalletFile(): String
    fun importWallet(privateKey: String): String
    fun loadCredentials(from: String): Credentials
    fun loadCredentials(walletFile: File): Credentials
    fun deleteAllWallets()

    val walletFiles: List<File>

    companion object {
        //TODO secure the password
        internal const val WALLET_FILE_PASSWORD = "wallet_file_password"
        internal const val WALLET_DIRECTORY = "wallets"
    }
}

@Singleton
class WalletHelperImpl @Inject constructor(
    private val context: Context
) : WalletHelper {

    init {
        setUpBouncyCastle()
    }

    override fun generateLightNewWalletFile(): String {
        return WalletUtils.generateLightNewWalletFile(WALLET_FILE_PASSWORD, walletDirectory)
    }

    override fun importWallet(privateKey: String): String {
        return WalletUtils.generateWalletFile(
            WALLET_FILE_PASSWORD,
            ECKeyPair.create(BigInteger(privateKey, 16)),
            walletDirectory,
            false
        )
    }

    override fun loadCredentials(from: String): Credentials {
        return loadCredentials(requireNotNull(getWalletFile(from)))
    }

    override fun loadCredentials(walletFile: File): Credentials {
        return WalletUtils.loadCredentials(WALLET_FILE_PASSWORD, walletFile)
    }

    override fun deleteAllWallets() {
        walletDirectory.deleteRecursively()
        walletDirectory.mkdirs()
    }

    override val walletFiles: List<File>
        get() = walletDirectory.listFiles()?.asList() ?: emptyList()

    private fun getWalletFile(walletAddress: String): File? {
        return walletDirectory.listFiles { _, name ->
            name.contains(walletAddress.substring(2).toLowerCase())
        }?.firstOrNull()
    }

    private val walletDirectory: File
        get() = File("${context.filesDir.absolutePath}/${WalletHelper.WALLET_DIRECTORY}").apply {
            if (!exists() || !isDirectory) {
                mkdirs()
            }
        }

    private fun setUpBouncyCastle() {
        val provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME)
            ?: // Web3j will set up the provider lazily when it's first used.
            return

        if (provider is BouncyCastleProvider) {
            // BC with same package name, shouldn't happen in real life.
            return
        }

        // Android registers its own BC provider. As it might be outdated and might not include
        // all needed ciphers, we substitute it with a known BC bundled in the app.
        // Android's BC has its package rewritten to "com.android.org.bouncycastle" and because
        // of that it's possible to have another BC implementation loaded in VM.
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME)
        Security.insertProviderAt(BouncyCastleProvider(), 1)
    }
}
