package com.cm.leadapp.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.util.Locale

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun String.firstCapital(): String = this.replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(
        Locale.ROOT
    ) else it.toString()
}