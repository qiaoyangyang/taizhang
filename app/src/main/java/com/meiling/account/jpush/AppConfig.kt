package com.meiling.account.jpush

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Bundle

@SuppressLint("StaticFieldLeak")
object AppConfig {

    const val TAG = "KotlinMVP"

    var debug = false

    private var application: Application? = null

    private  var activitys: Activity? = null

    /**
     * Init, it must be call before used .
     */
    fun init(application: Application) {
        AppConfig.application = application

    }

    fun getApplication(): Application {
        if (application == null) {
            throw RuntimeException("Please init in Application#onCreate first.")
        }
        return application!!
    }

    fun openDebug() {
        debug = true
    }

    fun initActivity(applications: Application) {
        applications.registerActivityLifecycleCallbacks(lifcycleCallBack)
    }

    fun getActivity(): Activity? {
        if (activitys == null) {
            return null
        }
        return activitys
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

}