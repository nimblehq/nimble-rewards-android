package com.nimble.nimblerewards.data.repositories

import com.nimble.nimblerewards.data.gateways.WalletHelper
import io.reactivex.Completable
import io.reactivex.Single
import org.web3j.crypto.Credentials
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

interface WalletRepository {
    fun generateWallet(): Single<String>
    fun importWallet(privateKey: String): Single<String>
    fun loadWalletsCredentials(): Single<List<Credentials>>
    fun loadWalletCredentials(walletFile: File): Single<Credentials>
    fun deleteWallets(): Completable
}

@Singleton
class WalletRepositoryImpl @Inject constructor(
    private val walletHelper: WalletHelper
) : WalletRepository {
    override fun generateWallet(): Single<String> {
        return Single.fromCallable {
            walletHelper.generateLightNewWalletFile()
        }
    }

    override fun importWallet(privateKey: String): Single<String> {
        return Single.fromCallable {
            walletHelper.importWallet(privateKey)
        }
    }

    override fun loadWalletsCredentials(): Single<List<Credentials>> {
        return Single.fromCallable {
            walletHelper.walletFiles.map {
                walletHelper.loadCredentials(it)
            }
        }
    }

    override fun loadWalletCredentials(walletFile: File): Single<Credentials> {
        return Single.fromCallable {
            walletHelper.loadCredentials(walletFile)
        }
    }

    override fun deleteWallets(): Completable {
        return Completable.fromCallable {
            walletHelper.deleteAllWallets()
        }
    }
}
