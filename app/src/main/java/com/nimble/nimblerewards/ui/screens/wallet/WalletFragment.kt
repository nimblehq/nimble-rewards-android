package com.nimble.nimblerewards.ui.screens.wallet

import android.annotation.SuppressLint
import android.text.SpannableStringBuilder
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.core.text.bold
import androidx.core.text.color
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.nimble.nimblerewards.R
import com.nimble.nimblerewards.ui.common.BaseFragment
import com.nimble.nimblerewards.ui.screens.signin.SignInActivity
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_wallet.*
import java.lang.String.format
import java.math.BigDecimal

class WalletFragment : BaseFragment<WalletViewModel>() {

    override val layoutResource = R.layout.fragment_wallet

    override val hasSupportActionBar = true

    override fun viewModel(): WalletViewModel =
        ViewModelProviders.of(this, viewModelFactory)
            .get(WalletViewModel::class.java)

    override fun configure() {
        viewModel.openSignIn
            .subscribe { SignInActivity.launch(requireActivity()) }
            .bindForDisposable()

        viewModel.wallet
            .subscribe { wallet ->
                tvUsdBalance.text = getBalanceText(wallet.totalBalanceInUsd, "$ ")
                tvEthereumSymbol.text = getSymbolText("ETH")
                tvEthereumBalance.text = getBalanceText(wallet.ethBalance)
                tvNimbleGoldSymbol.text = getSymbolText(wallet.nbgSymbol)
                tvNimbleGoldBalance.text = getBalanceText(wallet.nbgBalance)
                tvWalletAddress.text = getString(R.string.wallet_address, wallet.address)
            }
            .bindForDisposable()

        tvTransferEth.setOnClickListener {
            findNavController().navigate(R.id.action_walletFragment_to_transferFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadWallet()
            .subscribeBy(onError = toast::display)
            .bindForDisposable()
    }

    @SuppressLint("DefaultLocale")
    private fun getBalanceText(balance: BigDecimal, prefix: String = "") =
        SpannableStringBuilder()
            .color(getColor(resources, R.color.eth_balance, null)) {
                bold {
                    append(prefix)
                    append(format("%.2f", balance))
                }
            }

    private fun getSymbolText(symbol: String) =
        SpannableStringBuilder()
            .color(getColor(resources, R.color.eth_balance, null)) {
                bold {
                    append(symbol)
                }
            }
}
