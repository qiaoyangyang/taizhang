package com.meiling.common.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.github.chrisbanes.photoview.PhotoView
import com.meiling.common.loadUrl

class ImageAdapter(private val context: Context, private val imageUrls: List<String>) :
    PagerAdapter() {
    private var mOnItemClickListener: OnItemClickListener? = null
    override fun getCount(): Int {
        return imageUrls.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val url = imageUrls[position]
        val photoView = PhotoView(context)
        photoView.scaleType = ImageView.ScaleType.FIT_CENTER
        photoView.setOnClickListener {
            if (mOnItemClickListener != null) {
                mOnItemClickListener!!.onClick()
            }
        }
        photoView.setOnLongClickListener {
            if (mOnItemClickListener != null) {
                mOnItemClickListener!!.onLongCLick(position, imageUrls[position])
            }
            false
        }
        photoView.loadUrl(url)
        container.addView(photoView)
        return photoView
    }

    fun setOnItemClickListener(onItemClickListener:  OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onClick()
        fun onLongCLick(postion: Int, url: String?)
    }

}