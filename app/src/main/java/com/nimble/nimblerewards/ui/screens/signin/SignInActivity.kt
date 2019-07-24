package com.nimble.nimblerewards.ui.screens.signin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.view.setMargins
import androidx.lifecycle.ViewModelProviders
import com.nimble.nimblerewards.R
import com.nimble.nimblerewards.ui.common.BaseActivity
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_signin.*

class SignInActivity : BaseActivity<SignInViewModel>() {

    override val layoutResource = R.layout.activity_signin

    override fun viewModel(): SignInViewModel? =
        ViewModelProviders.of(this, viewModelFactory)
            .get(SignInViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btCreateNewWallet.setOnClickListener(::createNewWallet)
        btImportYourWallet.setOnClickListener(::showDialogToImportWallet)
    }

    override fun onBackPressed() {
        // Disable the back press
    }

    private fun createNewWallet(ignore: View) {
        requireNotNull(viewModel)
            .createWallet()
            .subscribeBy(
                onSuccess = { finish() },
                onError = toast::display
            )
            .bindForDisposable()
    }

    private fun importWallet(privateKey: String) {
        requireNotNull(viewModel)
            .importWallet(privateKey)
            .subscribeBy(
                onSuccess = { finish() },
                onError = toast::display
            )
            .bindForDisposable()
    }

    // FIXME: Hacky code, it should be removed
    private fun showDialogToImportWallet(ignored: View) {
        val privateKeyEditText = EditText(this).apply { hint = "Private Key" }
        val frameLayout = FrameLayout(this).apply {
            privateKeyEditText.apply {
                addView(this)
                (layoutParams as? ViewGroup.MarginLayoutParams)?.setMargins(35)
            }
        }

        AlertDialog.Builder(this)
            .setView(frameLayout)
            .setNegativeButton("Cancel") { _, _ -> }
            .setPositiveButton("OK") { _, _ ->
                val privateKey = privateKeyEditText.text.toString()
                if (privateKey.isNotEmpty()) {
                    importWallet(privateKey)
                }
            }.show()
    }

    companion object {
        fun launch(activity: Activity) {
            activity.startActivity(Intent(activity, SignInActivity::class.java))
        }
    }
}
