package com.nimble.nimblerewards.ui.common

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import com.nimble.nimblerewards.R
import com.nimble.nimblerewards.di.ViewModelFactory
import com.nimble.nimblerewards.ui.customviews.Toaster
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

abstract class BaseActivity<VM : BaseViewModel> : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var toast: Toaster

    protected val viewModel: VM? by lazy { viewModel() }

    private val disposables = CompositeDisposable()

    protected open fun viewModel(): VM? = null

    @get:LayoutRes
    protected abstract val layoutResource: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResource)
        setupActionBar()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    protected open fun setupActionBar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar?.let {
            setSupportActionBar(it)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
    }

    protected fun Disposable.bindForDisposable() {
        disposables.add(this)
    }
}
