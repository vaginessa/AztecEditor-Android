package org.wordpress.aztec.glideloader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import com.bumptech.glide.Glide
import com.bumptech.glide.request.Request
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SizeReadyCallback
import com.bumptech.glide.request.target.Target
import org.wordpress.aztec.Html

class GlideImageLoader(private val context: Context) : Html.ImageGetter {

    override fun loadImage(source: String, callbacks: Html.ImageGetter.Callbacks, maxWidth: Int) {
        Glide.with(context).load(source).asBitmap().fitCenter().into(object : Target<Bitmap> {
            override fun onLoadStarted(placeholder: Drawable?) {
                callbacks.onImageLoading(placeholder)
            }

            override fun onLoadFailed(e: Exception?, errorDrawable: Drawable?) {
                callbacks.onImageFailed()
            }

            override fun onResourceReady(resource: Bitmap?, glideAnimation: GlideAnimation<in Bitmap>?) {
                // By default, BitmapFactory.decodeFile sets the bitmap's density to the device default so, we need
                // to correctly set the input density to 160 ourselves.
                resource?.density = DisplayMetrics.DENSITY_DEFAULT
                callbacks.onImageLoaded(BitmapDrawable(context.resources, resource))
            }

            override fun onLoadCleared(placeholder: Drawable?) {}

            override fun getSize(cb: SizeReadyCallback?) {
                cb?.onSizeReady(maxWidth, Target.SIZE_ORIGINAL)
            }

            override fun setRequest(request: Request?) {
            }

            override fun getRequest(): Request? {
                return null
            }

            override fun onStart() {
            }

            override fun onStop() {
            }

            override fun onDestroy() {
            }
        })
    }
}
