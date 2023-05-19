package com.meiling.oms.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.util.Log
import androidx.annotation.Nullable
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.amap.api.services.core.PoiItem
import com.blankj.utilcode.util.ActivityUtils
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import com.meiling.common.activity.BaseVmActivity
import com.meiling.common.constant.SPConstants
import com.meiling.common.utils.InputTextManager
import com.meiling.common.utils.MMKVUtils
import com.meiling.common.utils.PermissionUtilis
import com.meiling.common.utils.RegularUtils
import com.meiling.oms.bean.PoiVo
import com.meiling.oms.databinding.ActivityNewlyBuiltStoreBinding
import com.meiling.oms.dialog.MineExitDialog
import com.meiling.oms.viewmodel.MainViewModel2
import com.meiling.oms.viewmodel.StoreManagementViewModel
import com.meiling.oms.widget.KeyBoardUtil
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast

//新建门店
@Route(path = "/app/NewlyBuiltStoreActivity")
class NewlyBuiltStoreActivity :
    BaseVmActivity<StoreManagementViewModel>() {
    private val REQUEST_CODE = 1000
    lateinit var mDatabind: ActivityNewlyBuiltStoreBinding
    lateinit var mainViewModel: MainViewModel2
    override fun initView(savedInstanceState: Bundle?) {
        MainActivity.mainActivity?.let {
            mainViewModel =
                ViewModelProvider(it).get(MainViewModel2::class.java)
        } ?: run {
            mainViewModel =
                ViewModelProvider(this@NewlyBuiltStoreActivity).get(MainViewModel2::class.java)
        }

    }

    override fun onLeftClick(view: View) {
        if(fromIntent=="regist"){
            val dialog: MineExitDialog =
                MineExitDialog().newInstance("温馨提示", "确定退出当前页面吗？", "取消", "确认", false)
            dialog.setOkClickLister {
                dialog.dismiss()
                startActivity(Intent(this,LoginActivity::class.java))
                ActivityUtils.finishAllActivities()
            }
            dialog.show(supportFragmentManager)
        }else{
            finish()
        }

    }
    override fun onBackPressed() {
        if(fromIntent=="regist"){
            val dialog: MineExitDialog =
                MineExitDialog().newInstance("温馨提示", "确定退出当前页面吗？", "取消", "确认", false)
            dialog.setOkClickLister {
                dialog.dismiss()
                startActivity(Intent(this,LoginActivity::class.java))
                ActivityUtils.finishAllActivities()
            }
            dialog.show(supportFragmentManager)
        }else{
            finish()
        }

    }

    private val ACCESS_FINE_LOCATION = 1
    var address = ""
    var lat = ""
    var lon = ""
    var provinceCode = ""
    var adCode = ""
    var cityName = ""
    var poiItem: PoiItem? = null
    var tenantId =""
    var adminViewId=""
    var fromIntent=""
    var account=""//管理员账号，默认注册时输入的手机号
    var pwd=""
    var name=""//品牌名称，默认企业名称的简称
    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // 处理返回的结果
            lon = data.getStringExtra("lon").toString()
            lat = data.getStringExtra("lat").toString()
            address = data.getStringExtra("address").toString()
            poiItem = data.getParcelableExtra("poiItem")
            provinceCode = poiItem?.provinceCode!!
            adCode = poiItem?.adCode!!
            cityName = poiItem?.cityName!!
            mDatabind.etStoreAddress.text = address
        }
    }


    override fun initDataBind() {
        mDatabind = ActivityNewlyBuiltStoreBinding.inflate(layoutInflater)
        setContentView(mDatabind.root)


    }

    var id = ""
    override fun initData() {
        super.initData()
        mDatabind.viewModel = mViewModel
        mDatabind.lifecycleOwner = this
        id = intent.getStringExtra("id").toString()
        tenantId = intent.getStringExtra("tenantId").toString()
        adminViewId= intent.getStringExtra("adminViewId").toString()
        fromIntent=intent.getStringExtra("fromIntent").toString()
        account=intent.getStringExtra("account").toString()//管理员账号
        pwd=intent.getStringExtra("pwd").toString()
        name=intent.getStringExtra("name").toString()

        if(fromIntent=="regist"){
            mDatabind.tvGoOn.text="下一步"
            mViewModel.PoiVoBean.value?.poiVo?.name=name
            mViewModel.PoiVoBean.value?.poiVo?.phone=account
        }
        mDatabind.tvGoOn.let {
            InputTextManager.with(this)
                .addView(mDatabind.etStoreName)
                .addView(mDatabind.etStoreTelephone)
                .addView(mDatabind.etStoreNumber)
                .addView(mDatabind.etStoreAddress)
                .setMain(it)
                .build()
            KeyBoardUtil.hideKeyBoard(this, mDatabind.tvGoOn)
        }
        if (!TextUtils.isEmpty(id) && id != "null") {


            mViewModel.poi(id)

        }
        mDatabind.tvGoOn.setSingleClickListener {
            if (TextUtils.isEmpty(mViewModel.PoiVoBean.value?.poiVo?.name)) {
                showToast("请输入门店名称")
                return@setSingleClickListener
            }
            if (TextUtils.isEmpty(mViewModel.PoiVoBean.value?.poiVo?.sinceCode)) {
                showToast("请输入门店名称")
                return@setSingleClickListener
            }
            if (TextUtils.isEmpty(mViewModel.PoiVoBean.value?.poiVo?.phone)) {
                showToast("请输入门店电话")
                return@setSingleClickListener
            }
            if (TextUtils.isEmpty(mViewModel.PoiVoBean.value?.poiVo?.storeaddress)) {
                showToast("请选择门店地址")
                return@setSingleClickListener
            }
            if (!RegularUtils.REGEX_TEL_phone(
                    mViewModel.PoiVoBean.value?.poiVo?.phone!!
                )
            ) {
                showToast("门店电话格式错误")
                return@setSingleClickListener
            }
            if (!TextUtils.isEmpty(id) && id != "null") {

            }

            if (mViewModel.PoiVoBean.value?.poiVo?.sinceCode!!.toString().length<2){
                showToast("门店编号仅允许输入2-20个字母/数字")
                return@setSingleClickListener
            }


           if(fromIntent=="regist"){
                MMKVUtils.putString(SPConstants.adminViewId,adminViewId)
                MMKVUtils.putString(SPConstants.tenantId,tenantId)
                mViewModel.poiaddFromRegist(
                    lat,
                    lon,
                    provinceCode,
                    "",
                    adCode,
                    cityName.replace("市", ""), id
                )
            }else {
                mViewModel.poiadd(
                    lat,
                    lon,
                    provinceCode,
                    "",
                    adCode,
                    cityName.replace("市", ""), id
                )
            }



        }
        mDatabind.etStoreAddress.setOnClickListener {


            XXPermissions.with(this).permission(PermissionUtilis.Group.LOCAL)
                .request(object : OnPermissionCallback {
                    override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                        if (!allGranted) {
                            showToast("获取部分权限成功，但部分权限未正常授予")
                            return
                        }
                        //   startActivity(Intent(this@NewlyBuiltStoreActivity,NewOrderChangeAddressMapActivity::class.java))
                        // initStart()
                        ARouter.getInstance().build("/app/NewOrderChangeAddressMapActivity")
                            .withString("title", "门店地址")
                            .navigation(this@NewlyBuiltStoreActivity, REQUEST_CODE)
                    }

                    override fun onDenied(
                        permissions: MutableList<String>,
                        doNotAskAgain: Boolean
                    ) {
                        if (doNotAskAgain) {
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(
                                this@NewlyBuiltStoreActivity,
                                permissions
                            )
                        } else {
                            showToast("授权失败，请检查权限")
                        }
                    }
                })

////            var intent = Intent(this, OrderChangeAddressMapActivity::class.java)
////            requestDataLauncher.launch(intent)
//            if (ContextCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                ) == PackageManager.PERMISSION_GRANTED
//            ) {
//                // 如果有权限，拨打电话
//                ARouter.getInstance().build("/app/OrderChangeAddressMapActivity")
//                    .navigation(this, REQUEST_CODE)
//            } else {
//                // 如果没有权限，申请权限
//                ActivityCompat.requestPermissions(
//                    this,
//                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                    ACCESS_FINE_LOCATION
//                )
//            }
        }

    }

    var poiVo = PoiVo()

    @SuppressLint("SuspiciousIndentation")
    override fun createObserver() {
        mViewModel.poidata.onStart.observe(this) {
            showLoading("")
        }
        mViewModel.poidata.onSuccess.observe(this) {
            disLoading()
            val x = it.poiVo?.address!!.split("@@", " ","&&")
            if (x.size != 0) {
                it?.poiVo?.storeaddress = x[0]
                it?.poiVo?.etdetailedaddress = x[1]
            }
            lat = it?.poiVo?.lat!!
            lon = it?.poiVo?.lon!!
            cityName = it.poiVo?.cityName!!
            adCode = it.poiVo?.districtCode!!
            provinceCode = it?.poiVo?.provinceCode!!

            mViewModel.PoiVoBean.value = it

        }
        mViewModel.poidata.onError.observe(this) {
            disLoading()
            showToast(it.msg)
        }
        mViewModel.poiaddpoidata.onStart.observe(this) {
            showLoading("")
        }
        mViewModel.poiaddpoidata.onSuccess.observe(this) {
            disLoading()
            showToast("门店信息保存成功")
            mainViewModel.getByTenantId.value = mainViewModel.getByTenantId.value?.copy(poi = 1)
            if(fromIntent=="regist"){
                startActivity(Intent(this,
                    BindingLogisticsActivity::class.java)
                    .putExtra("tenantId", tenantId)
                    .putExtra("account",account)
                    .putExtra("pwd",pwd)
                    .putExtra("name", name)
                    .putExtra("poid",it)
                    .putExtra("from",fromIntent))
            }else
            finish()
        }
        mViewModel.poiaddpoidata.onError.observe(this) {
            disLoading()
            showToast(it.msg)
        }
    }

    override fun isLightMode(): Boolean {
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ACCESS_FINE_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 如果有权限跳转
                ARouter.getInstance().build("/app/OrderChangeAddressMapActivity")
                    .navigation(this, REQUEST_CODE)
            } else {
                showToast("您已经禁止权限，请手动开启")
                // 如果用户拒绝了权限，可以在这里处理相应的逻辑
            }
        }
    }



    private fun isPasswordValid(password: String): Boolean {

        Log.d("lwq", "=========1111${password}")
        // 判断是否是纯数字或纯字母
        if (password.matches(Regex("\\d+")) || password.matches(Regex("[a-zA-Z]+"))) {
            return false
        }

        // 判断是否包含字母和数字
        if (password.matches(Regex("[a-zA-Z]+")) && password.matches(Regex("\\d+"))) {
            return true
        }
        return true
    }


}