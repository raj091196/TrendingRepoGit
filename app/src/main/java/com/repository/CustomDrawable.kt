package com.repository

import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat


class CustomDrawable(private val context: Application) {

    fun setDrawableColour(colourCode: Int): Drawable? {
        val drawable = AppCompatResources.getDrawable(context, R.drawable.custom_drawable)
        drawable?.let {
            val drawableCompact = DrawableCompat.wrap(drawable)
            DrawableCompat.setTint(drawableCompact, colourCode)
            return drawable
        }
        return AppCompatResources.getDrawable(context, R.drawable.custom_drawable)
    }
}