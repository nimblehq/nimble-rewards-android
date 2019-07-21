package com.nimble.nimblerewards.ui.screens.wallet

import android.annotation.SuppressLint
import android.text.SpannableStringBuilder
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.core.text.bold
import androidx.core.text.color
import androidx.lifecycle.ViewModelProviders
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
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadWallet()
            .subscribeBy(
                onSuccess = {
                    tvWalletAddress.text = getString(R.string.wallet_address, it.address)
                    tvEthBalance.text = getBalanceText(it.ethBalance)
                },
                onError = ::displayErrorMessage
            )
            .bindForDisposable()
    }

    @SuppressLint("DefaultLocale")
    private fun getBalanceText(balance: BigDecimal) =
        SpannableStringBuilder()
            .color(getColor(resources, R.color.eth_balance, null)) {
                bold {
                    append(format("%.3f", balance))
                }
            }
}
