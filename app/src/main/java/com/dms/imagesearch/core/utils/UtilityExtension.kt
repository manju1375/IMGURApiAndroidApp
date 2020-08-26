package com.dms.imagesearch.core.utils


import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

fun Activity.setupActionBar(
    title: String,
    displayHome: Boolean = false,
    hasOptionsMenu: Boolean = false
) {
    (this as? AppCompatActivity)?.invalidateOptionsMenu()
    (this as? AppCompatActivity)?.supportActionBar?.apply {
        this.title = title
        setDisplayShowHomeEnabled(displayHome)
        setDisplayHomeAsUpEnabled(displayHome)
        this.show()
    }
}

fun View.hideKeyboard() {
    val inputMethodManager = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun Activity.hideKeyboard() {
    // Calls Context.hideKeyboard
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    view.hideKeyboard()
}

fun Activity.saveSelectedImg(imgId: String) {
    val filename = "imagesdb"     // (1)
    val pref = getSharedPreferences(filename, Context.MODE_PRIVATE)   // (2)
    val editor = pref.edit()

    editor.putString("selectedImg", imgId)
    editor.apply()
}

fun Activity.fetchSelectedImg(): String {
    val pref = getSharedPreferences("imagesdb", Context.MODE_PRIVATE) // (1)
    return pref.getString("selectedImg", "")!!
}
