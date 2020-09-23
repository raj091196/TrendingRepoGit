package com.repository

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

object GlideAppImpl {

    /**
     *  @param context application context or context of the any Components
     *  @param source url of the image
     *  @param imageView Image View to load the Image
     *  @param errorImage error Drawable Image
     */
    fun loadImage(
        context: Context,
        source: String,
        imageView: ImageView,
        errorImage: Drawable?
    ) {

        val requestOption = RequestOptions().placeholder(errorImage)
            .override(30, 30)
            .error(errorImage)
            .priority(Priority.HIGH)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)

        GlideApp.with(context).load(source).apply(requestOption).dontAnimate().into(imageView)
    }
}