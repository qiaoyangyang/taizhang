package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.databinding.ActivityPostDetailBinding

@Route(path = "/app/PostDetailActivity")
class PostDetailActivity : BaseActivity<BaseViewModel, ActivityPostDetailBinding>() {


    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityPostDetailBinding {
        return ActivityPostDetailBinding.inflate(layoutInflater)
    }


    private var b = true

    override fun initListener() {
        mDatabind.aivImg.setOnClickListener {
            if (b) {
                ARouter.getInstance().build("/app/GiveActivity").navigation()
            } else {
                ARouter.getInstance().build("/app/CommentActivity").navigation()
            }
            b = !b
        }
        mDatabind.aivBack.setOnClickListener { finish() }
    }

}