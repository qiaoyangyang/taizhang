package com.meiling.common

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.load.resource.bitmap.RoundedCorners


fun ImageView.loadUrl(url: String?) =
    GlideApp.with(this).load(url).fitCenter().into(this)

fun ImageView.loadDrawable(@DrawableRes drawableRes: Int?) =
    GlideApp
        .with(this)
        .load(drawableRes)
        .fitCenter()
        .into(this)

fun ImageView.loadUrlCenterCrop(url: String?, roundingRadius: Int = 1) =
    GlideApp
        .with(this)
        .load(url)
        .transform(CenterCrop(), RoundedCorners(roundingRadius))
        .into(this)

fun ImageView.loadUrlCenterCropDp(
    url: String?,
    roundingRadius: Int = 1,
    @DrawableRes placeholder: Int = R.drawable.brvah_sample_footer_loading
) =
    GlideApp
        .with(this)
        .load(url)
        .placeholder(placeholder)
        .fallback(placeholder)
        .error(placeholder)
        .transform(CenterCrop(), RoundedCorners(dp2px(this.context, roundingRadius)))
        .into(this)


fun ImageView.loadUrlCircle(url: String?) =
    GlideApp
        .with(this)
        .load(url)
        .transform(CenterCrop(), CircleCrop())
        .into(this)


fun ImageView.loadUrlCenterCropCorner(
    url: String?,
    topLeft: Float,
    topRight: Float,
    bottomRight: Float,
    bottomLeft: Float
) =
    GlideApp
        .with(this)
        .load(url)
        .transform(CenterCrop(), GranularRoundedCorners(topLeft, topRight, bottomRight, bottomLeft))
        .into(this)


fun ImageView.loadFileUri(uri: Uri) {
    GlideApp
        .with(this)
        .load(uri.path)
        .skipMemoryCache(true)
        .into(this)
}

fun dp2px(context: Context, dpValue: Int): Int {
    val scale = context.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}