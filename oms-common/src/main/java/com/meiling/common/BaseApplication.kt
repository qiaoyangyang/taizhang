package com.meiling.common

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.Utils
import com.meiling.common.utils.CrashManagerUtil
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.mmkv.MMKV
import com.tencent.smtt.sdk.QbSdk

open class BaseApplication : Application(), ViewModelStoreOwner {

    private var mFactory: ViewModelProvider.Factory? = null
    private lateinit var mAppViewModelStore: ViewModelStore


    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this)
        Logger.addLogAdapter(AndroidLogAdapter())
        MMKV.initialize(this);
        this.registerActivityLifecycleCallbacks(lifcycleCallBack)
        QbSdk.initX5Environment(this, object : QbSdk.PreInitCallback {
            override fun onCoreInitFinished() {

            }

            override fun onViewInitFinished(p0: Boolean) {

            }
        })
        CrashManagerUtil.getInstance(this).init()

        CrashReport.initCrashReport(this, "0e93bafb3e", false);

    }

    private var activitys: Activity? = null

    fun getActivity(): Activity? {
        if (activitys == null) {
            return null
        }
        return activitys
    }

    private fun getAppFactory(): ViewModelProvider.Factory {
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(this)
        }
        return mFactory as ViewModelProvider.Factory
    }

    fun getAppViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(this, this.getAppFactory())
    }


    override fun onLowMemory() {
        super.onLowMemory()
        GlideApp.get(this).onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        GlideApp.get(this).onTrimMemory(level)
    }

    private val lifcycleCallBack = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        }

        override fun onActivityStarted(activity: Activity) {
        }

        override fun onActivityResumed(activity: Activity) {
            activitys = activity
        }

        override fun onActivityPaused(activity: Activity) {
        }

        override fun onActivityStopped(activity: Activity) {
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        }

        override fun onActivityDestroyed(activity: Activity) {
        }


    }

    init {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(DefaultRefreshHeaderCreator { context, layout ->
            MaterialHeader(context)
        })
        SmartRefreshLayout.setDefaultRefreshFooterCreator(DefaultRefreshFooterCreator { context, layout ->
            ClassicsFooter(context).setDrawableSize(20f).setFinishDuration(0)
        })

    }
}