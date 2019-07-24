package com.nimble.nimblerewards.ui.screens.transfer

import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.nimble.nimblerewards.R
import com.nimble.nimblerewards.extensions.hideSoftKeyboard
import com.nimble.nimblerewards.ui.common.BaseFragment
import com.nimble.nimblerewards.ui.listeners.SimpleTextChangeListener
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_transfer.*

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
        activity?.hideSoftKeyboard()
        
        viewModel.transfer()
            .subscribeBy(
                onSuccess = {
                    toast.display(getString(R.string.transferred_successfully))
                    findNavController().popBackStack()
                },
                onError = toast::display
            )
            .bindForDisposable()
    }

    private fun verifyTargetWalletAddress(address: String) {
        viewModel.verifyWalletAddress(address)
            .subscribeBy(onError = toast::display)
            .bindForDisposable()
    }

    private fun verifyAmount(amount: String) {
        viewModel.verifyAmount(amount)
            .subscribeBy(onError = toast::display)
            .bindForDisposable()
    }
}
