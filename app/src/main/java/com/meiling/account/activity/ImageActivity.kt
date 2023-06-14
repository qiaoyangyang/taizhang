package com.meiling.account.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.gyf.immersionbar.BarHide
import com.meiling.common.activity.BaseActivity
import com.meiling.account.adapter.ImagePagerAdapter
import com.meiling.account.databinding.ActivityImageBinding
import com.meiling.account.viewmodel.VoucherinspectionViewModel
import com.meiling.account.widget.IntentKey


class ImageActivity : BaseActivity<VoucherinspectionViewModel, ActivityImageBinding>() {

    open fun start(context: Context, url: String) {
        val images: ArrayList<String> = ArrayList(1)
        images.add(url)
        start(context, images)
    }

    open fun start(context: Context, urls: ArrayList<String>?) {
        start(context, urls, 0)
    }

    open fun start(context: Context, urls: ArrayList<String>?, index: Int) {
        val intent = Intent(context, ImageActivity::class.java)
        intent.putExtra(IntentKey.PICTURE, urls)
        intent.putExtra(IntentKey.INDEX, index)
        context.startActivity(intent)
    }
    override fun initView(savedInstanceState: Bundle?) {
        // 设置状态栏和导航栏参数
        getStatusBarConfig()
            // 有导航栏的情况下，activity全屏显示，也就是activity最下面被导航栏覆盖，不写默认非全屏
            .fullScreen(true)
            // 隐藏状态栏
            .hideBar(BarHide.FLAG_HIDE_STATUS_BAR)
            // 透明导航栏，不写默认黑色(设置此方法，fullScreen()方法自动为true)
            .transparentNavigationBar()
            .init();
        mDatabind.pvImageIndicator.setViewPager(mDatabind.vpImagePager)
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityImageBinding {
        return ActivityImageBinding.inflate(layoutInflater)
    }
    var adapter: ImagePagerAdapter? = null
    override fun initData() {
        val images = intent.getStringArrayListExtra(IntentKey.PICTURE)
        adapter = ImagePagerAdapter(this, images)
        val index = intent.getIntExtra(IntentKey.INDEX, 0)
        if (images != null && images.size > 0) {
            mDatabind.vpImagePager.setAdapter(adapter)
            if (index != 0 && index <= images.size) {
               mDatabind.vpImagePager .setCurrentItem(index)
            }
        } else {
            finish()
        }
    }

}