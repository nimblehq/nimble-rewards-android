package com.nimble.nimblerewards.ui.screens.transfer

import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.nimble.nimblerewards.R
import com.nimble.nimblerewards.extensions.showSnackBar
import com.nimble.nimblerewards.ui.common.BaseFragment
import com.nimble.nimblerewards.ui.listeners.SimpleTextChangeListener
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_transfer.*
import org.web3j.protocol.core.methods.response.TransactionReceipt

class TransferFragment : BaseFragment<TransferViewModel>() {

    override val layoutResource = R.layout.fragment_transfer

    override val hasSupportActionBar = true

    override val toolbarIcon = R.drawable.ic_ethereum_white

    override fun viewModel(): TransferViewModel =
        ViewModelProviders.of(this, viewModelFactory)
            .get(TransferViewModel::class.java)

    override fun configure() {
        btSendEth.setOnClickListener(::transferEth)

        etTargetWalletAddress.addTextChangedListener(object : SimpleTextChangeListener {
            override fun textChanged(text: String) {
                verifyTargetWalletAddress(text)
            }
        })

        etTransferAmount.addTextChangedListener(object : SimpleTextChangeListener {
            override fun textChanged(text: String) {
                verifyAmount(text)
            }
        })
    }

    private fun transferEth(ignored: View) {
        viewModel.transfer()
            .subscribeBy(
                onSuccess = ::displayReceipt,
                onError = ::displayErrorMessage
            )
            .bindForDisposable()
    }

    private fun verifyTargetWalletAddress(address: String) {
        viewModel.verifyWalletAddressError(address)
            .subscribeBy(onError = ::displayErrorMessage)
            .bindForDisposable()
    }

    private fun verifyAmount(amount: String) {
        viewModel.verifyAmount(amount)
            .subscribeBy(onError = ::displayErrorMessage)
            .bindForDisposable()
    }

    private fun displayReceipt(receipt: TransactionReceipt) {
        view?.showSnackBar(receipt.status)
    }
}
