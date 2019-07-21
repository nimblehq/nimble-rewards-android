package com.nimble.nimblerewards.ui.screens.settings

import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.nimble.nimblerewards.R
import com.nimble.nimblerewards.ui.common.BaseFragment
import com.nimble.nimblerewards.ui.screens.signin.SignInActivity
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : BaseFragment<SettingsViewModel>() {

    override val layoutResource = R.layout.fragment_settings

    override val hasSupportActionBar = true

    override fun viewModel(): SettingsViewModel =
        ViewModelProviders.of(this, viewModelFactory)
            .get(SettingsViewModel::class.java)

    override fun configure() {
        viewModel.openSignIn
            .subscribe { SignInActivity.launch(requireActivity()) }
            .bindForDisposable()

        btSignOut.setOnClickListener(::signOut)
    }

    private fun signOut(ignore: View) {
        viewModel.signOut()
            .subscribeBy(
                onComplete = { /* do nothing */ },
                onError = ::displayErrorMessage
            )
            .bindForDisposable()
    }
}
