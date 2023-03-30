package com.meiling.common.utils

import android.widget.ImageView
import com.blankj.utilcode.util.Utils
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.meiling.common.GlideApp
import com.meiling.common.dp2px

object GlideAppUtils {

    fun loadUrl(imageView: ImageView, url: String) {
        GlideApp.with(Utils.getApp()).load(url).fitCenter().into(imageView)
    }

    fun loadUrlCenterCropDp(imageView: ImageView, url: String, roundingRadius: Int = 1) {
        GlideApp
            .with(Utils.getApp())
            .load(url)
            .transform(CenterCrop(), RoundedCorners(dp2px(Utils.getApp(), roundingRadius)))
            .into(imageView)
    }

    fun loadUrlCircle(imageView: ImageView, url: String) {
        GlideApp
            .with(Utils.getApp())
            .load(url)
            .transform(CenterCrop(), CircleCrop())
            .into(imageView)
    }
}