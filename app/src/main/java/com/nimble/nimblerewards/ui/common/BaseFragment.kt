package com.nimble.nimblerewards.ui.common

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nimble.nimblerewards.R
import com.nimble.nimblerewards.di.ViewModelFactory
import com.nimble.nimblerewards.ui.customviews.Toaster
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var toast: Toaster

    protected val viewModel: VM by lazy { viewModel() }

    private val disposables = CompositeDisposable()

    protected abstract val layoutResource: Int
    protected open val toolbarIcon: Int? = null
    private val toolbarIconSize by lazy {
        resources.getDimensionPixelSize(R.dimen.navigation_toolbar_icon_size)
    }

    protected open val hasSupportActionBar: Boolean = false

    abstract fun viewModel(): VM

    abstract fun configure()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutResource, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        configure()
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    @SuppressLint("RestrictedApi")
    override fun onResume() {
        super.onResume()
        (context as? AppCompatActivity)?.supportActionBar?.apply {
            setShowHideAnimationEnabled(false)
            if (hasSupportActionBar) {
                setTitle()
                show()
            } else {
                hide()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    protected fun Disposable.bindForDisposable() {
        disposables.add(this)
    }

    private fun setTitle() {
        val toolbarTitle = (activity as AppCompatActivity).findViewById<TextView>(R.id.toolbarTitle)
        toolbarTitle?.let { titleView ->
            titleView.text = findNavController().currentDestination?.label
            toolbarIcon?.let { toolbarIcon ->
                val icon = resources.getDrawable(toolbarIcon, null)
                icon?.setBounds(0, 0, toolbarIconSize, toolbarIconSize)
                icon?.setTint(Color.WHITE)
                titleView.setCompoundDrawables(icon, null, null, null)
            } ?: titleView.setCompoundDrawables(null, null, null, null)
        }
    }
}
