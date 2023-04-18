package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.databinding.ActivityAboutBinding
import com.meiling.oms.dialog.AboutKFDialog
import com.meiling.oms.viewmodel.LoginViewModel
import com.meiling.oms.widget.UpdateVersion
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast

@Route(path = "/app/AboutActivity")
class AboutActivity : BaseActivity<LoginViewModel, ActivityAboutBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun initListener() {
        mDatabind.slCopyUrl.setSingleClickListener { showToast("地址已经复制") }
        mDatabind.slXy.setSingleClickListener {
            ARouter.getInstance().build("/app/AgreementActivity").withString("YSXY", "0")
                .navigation()
        }
        mDatabind.slYs.setSingleClickListener {
            ARouter.getInstance().build("/app/AgreementActivity").withString("YSXY", "1")
                .navigation()
        }
        mDatabind.slVersion.setSingleClickListener {
            UpdateVersion.getUpdateVersion(this, "1")
//            showToast("最新版本")
        }
        mDatabind.stKf.setSingleClickListener {
            AboutKFDialog().newInstance().newInstance().show(supportFragmentManager)
        }
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityAboutBinding {
        return ActivityAboutBinding.inflate(layoutInflater)
    }
}