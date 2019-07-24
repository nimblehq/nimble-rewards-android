package com.nimble.nimblerewards.ui.listeners

import android.text.Editable
import android.text.TextWatcher

interface SimpleTextChangeListener : TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun afterTextChanged(p0: Editable) {
        textChanged(p0.toString())
    }

    fun textChanged(text: String)
}
