package com.meiling.common.target

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.Utils
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.meiling.common.GlideApp

class AppCustomTarget(private val imageView: ImageView, private val cover: String) :
    CustomTarget<Bitmap?>() {


    override fun onLoadCleared(placeholder: Drawable?) {}

    override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap?>?) {
        var width = bitmap.width
        var height = bitmap.height
        bitmap.recycle()
        val layoutParams = imageView.layoutParams
        layoutParams.height = ScreenUtils.getScreenWidth() * height / width/1
        imageView.layoutParams = layoutParams
        GlideApp.with(Utils.getApp()).load(cover).into(imageView)
    }
}