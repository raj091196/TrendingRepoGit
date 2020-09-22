package com.repository.extension

import android.graphics.drawable.Drawable
import android.util.Log.*
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import com.repository.BuildConfig
import java.util.*

fun Any.debugLog(message: String) {
    if (BuildConfig.DEBUG)
        d(javaClass.simpleName, message)
}

fun Any.infoLog(message: String) {
    if (BuildConfig.DEBUG)
        i(javaClass.simpleName, message)
}

fun Any.errorLog(message: String) {
    if (BuildConfig.DEBUG)
        e(javaClass.simpleName, message)
}

fun TextView.bindDrawableStart(drawable: Drawable?) {
    this.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
}

fun SearchView.search(function: (searchString: String) -> Unit) {

    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(s: String): Boolean {
            function(s)
            return true
        }

        override fun onQueryTextChange(searchString: String): Boolean {
            function(searchString)
            return true
        }
    })
}

fun String.containsIgnoreCase(value: String): Boolean {
    return this.toLowerCase(Locale.ROOT).contains(value.toLowerCase(Locale.ROOT))
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visibility(isVisible: Boolean) {
    if (isVisible) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}