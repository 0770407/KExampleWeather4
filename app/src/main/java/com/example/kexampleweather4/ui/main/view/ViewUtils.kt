package com.example.kexampleweather4.ui.main.view

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService
import com.google.android.material.snackbar.Snackbar
import java.lang.RuntimeException

fun View.myMethodShow() {
    this.visibility = View.VISIBLE
}

fun View.myMethodHide() {
    this.visibility = View.GONE
}

fun View.myShowSnackBar(
    text: String,
    actionText: String,
    action: (View) -> Unit,
    length: Int = Snackbar.LENGTH_INDEFINITE
) {
    Snackbar
        .make(
            this,
            text,
            length
        )
        .setAction("Reload") { action(this) }
        .show()
}

fun View.myHideKeyboard(): Boolean =
    try {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (e: RuntimeException) {
    false
}