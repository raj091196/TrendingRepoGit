package com.repository

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import androidx.annotation.ColorInt
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import javax.inject.Inject

class Utils @Inject constructor(private val context: Application) {

    fun getColouredDrawable(@ColorInt colourCode: Int): Drawable? {
        val drawable = AppCompatResources.getDrawable(context, R.drawable.custom_drawable)
        drawable?.let {
            val drawableCompact = DrawableCompat.wrap(drawable)
            DrawableCompat.setTint(drawableCompact, colourCode)
            return drawable
        }
        return AppCompatResources.getDrawable(context, R.drawable.custom_drawable)
    }

    fun hasInternet(): Boolean {
        val conMgr = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        @Suppress("DEPRECATION")
        return conMgr.activeNetworkInfo != null && conMgr.activeNetworkInfo!!.isConnected
    }
}