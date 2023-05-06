package com.meiling.oms.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import anetwork.channel.monitor.Monitor.start
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.meiling.common.activity.BaseActivity
import com.meiling.common.utils.SpannableUtils
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityTiktoKnindingBinding
import com.meiling.oms.viewmodel.MainViewModel2
import com.meiling.oms.viewmodel.StoreManagementViewModel
import com.meiling.oms.widget.showToast

class TiktokBindingActvity :
    BaseActivity<StoreManagementViewModel, ActivityTiktoKnindingBinding>() ,
    SpannableUtils.ontestonClick {
    lateinit var mainViewModel: MainViewModel2
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.etBindTenant.setText("7070780918415230991")
        SpannableUtils.setTiktokBindingTextcolor(
            this,
            getString(R.string.first_step),
            mDatabind.tvFirstStep,
            getString(R.string.first_step).length - 2,
            getString(R.string.first_step).length,
            R.color.pwd_1180FF, 0,this)
        SpannableUtils.setTiktokBindingTextcolor(
            this,
            getString(R.string.service),
            mDatabind.tvService,
            6,
            10,
            R.color.pwd_1180FF, 1,this
        )
        mDatabind.tvOk.setOnClickListener {
            if (TextUtils.isEmpty(mDatabind.etBindTenant.text.toString())) {
                showToast("请输入抖音来客的账号ID")
                return@setOnClickListener
            }

            mViewModel.bindTenant(mDatabind.etBindTenant.text.toString())

        }

        mainViewModel =
            ViewModelProvider(MainActivity.mainActivity!!).get(MainViewModel2::class.java)
    }

    var channelId: String = ""
    var poiId: String = ""
    var channename: String = ""

    override fun initData() {
        channelId = intent.getStringExtra("channelId").toString()
        poiId = intent.getStringExtra("poiId").toString()
        channename = intent.getStringExtra("channename").toString()
        mDatabind.TitleBar.title="绑定${channename}店铺"

    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.bindTenant.onStart.observe(this) {
            showLoading("")
        }
        mViewModel.bindTenant.onSuccess.observe(this) {
            disLoading()
            mainViewModel.getByTenantId.value = mainViewModel.getByTenantId.value?.copy(poi = 1)
            startActivity(
                Intent(this, BindingTiktokListActivity::class.java).putExtra(
                    "channelId",
                    channelId
                )
                    .putExtra("poiId", poiId).putExtra("channename",channename)
            )

        }
        mViewModel.bindTenant.onError.observe(this){
            disLoading()
            showToast(it.msg)
        }
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityTiktoKnindingBinding {
        return ActivityTiktoKnindingBinding.inflate(layoutInflater)
    }



    override fun ononClick(type: Int) {
        if (type==1){
            XXPermissions.with(this).permission(Permission.CALL_PHONE)
                .request(object : OnPermissionCallback {
                    override fun onGranted(permissions: List<String>, allGranted: Boolean) {
                        SpannableUtils.dialPhoneNumber("15535958281", this@TiktokBindingActvity)
                    }

                    override fun onDenied(permissions: List<String>, doNotAskAgain: Boolean) {
                        if (doNotAskAgain) {
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(this@TiktokBindingActvity, permissions)
                        } else {
                             showToast("授权失败，请检查权限");
                            //Toast.makeText(context,"授权失败，请检查权限");
                        }
                    }
                })
        }else{
           // ImageActivity.start("");
            ImageActivity().start(this,"https://img2.baidu.com/it/u=1617934108,135390230&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=889")
        }
    }
}