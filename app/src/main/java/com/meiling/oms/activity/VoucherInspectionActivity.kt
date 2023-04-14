package com.meiling.oms.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.app.ActivityCompat
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsScan
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.*
import com.meiling.common.utils.PermissionUtilis
import com.meiling.common.utils.TextDrawableUtils
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityVoucherinspectionBinding
import com.meiling.oms.dialog.CheckCouponInformationDidalog
import com.meiling.oms.dialog.CheckCouponInformationDidalog1
import com.meiling.oms.dialog.ShopDialog
import com.meiling.oms.viewmodel.VoucherinspectionViewModel
import com.meiling.oms.widget.showToast


//验券
class VoucherInspectionActivity :
    BaseActivity<VoucherinspectionViewModel, ActivityVoucherinspectionBinding>() {

    var type = ""
    var thrillBen = ArrayList<ThrillBen>()
    var shopBean = ArrayList<ShopBean>()
    var meituan: Meituan? = null
    override fun initView(savedInstanceState: Bundle?) {
        TextDrawableUtils.setRightDrawable(mDatabind.TitleBar.titleView, R.drawable.xia)
        //验券历史
        mDatabind.tvVoucherInspectionHistory.setOnClickListener {
            if (shopdata != null) {
                startActivity(
                    Intent(this, VoucherInspectionHistoryActivity::class.java)
                        .putExtra("shop", shopdata)
                        .putExtra("type", type)
                )
            }
        }
        //  输码验券
        mDatabind.tvInputBoredom.setOnClickListener {
            startActivity(
                Intent(this, InputBoredomActivity::class.java).putExtra("type", type)
            )
        }


        mDatabind.clScan.setOnClickListener {
            //requestPermission(CAMERA_REQ_CODE, DECODE)
            XXPermissions.with(this).permission(PermissionUtilis.Group.RICHSCAN).request(object :OnPermissionCallback{
                override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                    if (!allGranted) {
                        showToast("获取部分权限成功，但部分权限未正常授予")
                        return
                    }
                    setstartScan()

                }

                override fun onDenied(permissions: MutableList<String>, doNotAskAgain: Boolean) {
                    if (doNotAskAgain) {
                        showToast("被永久拒绝授权，请手动授予录音和日历权限")
                        // 如果是被永久拒绝就跳转到应用权限系统设置页面
                        XXPermissions.startPermissionActivity(this@VoucherInspectionActivity,permissions)
                    } else {
                        showToast("获取录音和日历权限失败")
                    }
                }

            })
        }

    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityVoucherinspectionBinding {
        return ActivityVoucherinspectionBinding.inflate(layoutInflater)
    }

    override fun initData() {
        super.initData()
        type = intent?.getStringExtra("type").toString()
        mViewModel.cityshop(type)


    }

    override fun onTitleClick(view: View) {
        super.onTitleClick(view)

        mViewModel.shopBean.onSuccess.observe(this) {
            //DataPickerUtitl.setpickData(this,it)
            if (it.size != 0) {
                var shopDialog = ShopDialog().newInstance(it)

                shopDialog.setOnresilience(object : ShopDialog.Onresilience {
                    override fun resilience(
                        cityid: Int,
                        cityidname: String,
                        shopid: Int,
                        shop: Shop
                    ) {
                        mViewModel.Shop.onSuccess.postValue(shop)
                        shopId = shop?.id.toString()
                        shopdata = shop
                        mDatabind.TitleBar.titleView.text = cityidname + shop.name
                    }

                    override fun Ondismiss() {
                    }

                })
                shopDialog.show(supportFragmentManager)
            }

        }

    }

    override fun createObserver() {

        mViewModel.thrillBen.onSuccess.observe(this) {
            if (it.size != 0) {
                var checkCouponInformationDidalog = CheckCouponInformationDidalog().newInstance(it)
                checkCouponInformationDidalog.setOnresilience(object :
                    CheckCouponInformationDidalog.Onresilience {
                    override fun resilience(encryptedCode: String) {
                        mViewModel.verify(shopId, encryptedCode)
                    }

                })

                checkCouponInformationDidalog.show(supportFragmentManager)
            }


        }

        mViewModel.shopBean.onSuccess.observe(this) {
            if (it.size != 0) {
                shopdata = it[0].shopList?.get(0)
                shopId = it.get(0).shopList?.get(0)?.id!!
                mDatabind.TitleBar.titleView.text =
                    it.get(0).name + "/" + it.get(0).shopList?.get(0)?.name
            }
        }

        //核销成功
        mViewModel.verifythrillBen.onSuccess.observe(this) {
            if (type == "1") {
                var it1 = it as ArrayList<ThrillItem>
                startActivity(
                    Intent(this, WriteOffActivity::class.java).putExtra(
                        "thrillitem",
                        it1
                    ).putExtra("shopId", shopId)
                )
            } else {
                Log.d("yjk", "核销成功mei---")

            }

        }
        //确认核销失败
        mViewModel.verifythrillBen.onError.observe(this) {
            showToast("${it.msg}")
        }
        //扫码核销失败
        mViewModel.thrillBen.onError.observe(this) {
            showToast("${it.msg}")
        }
        //美团扫码返回
        mViewModel.meituan.onSuccess.observe(this) {

            var checkCouponInformationDidalog = CheckCouponInformationDidalog1().newInstance(it)
            checkCouponInformationDidalog.setOnresilience(object :
                CheckCouponInformationDidalog1.Onresilience {
                override fun resilience(encryptedCode: String, count: String, mode: Meituan) {
                    meituan = mode
                    mViewModel.consume(encryptedCode, count, shopId)
                }

            })

            checkCouponInformationDidalog.show(supportFragmentManager)
        }
        mViewModel.meituan.onError.observe(this) {
            showToast("${it.msg}")
        }

        mViewModel.consume.onSuccess.observe(this) {
            startActivity(
                Intent(this, MeituanActivity::class.java).putExtra(
                    "meituan",
                    meituan
                ).putExtra("shopId", shopId).putExtra("code", it)
            )
        }


    }

    //编辑请求权限
    open fun requestPermission(requestCode: Int, mode: Int) {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE),
            requestCode
        )
    }

    val DECODE = 1
    val CAMERA_REQ_CODE = 111;
    val REQUEST_CODE_SCAN_ONE = 0X01

    //权限申请返回
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (permissions == null || grantResults == null) {
            return
        }
        if (grantResults.size < 2 || grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED) {
            return
        }
        if (requestCode == CAMERA_REQ_CODE) {
            ScanUtil.startScan(
                this,
                REQUEST_CODE_SCAN_ONE,
                HmsScanAnalyzerOptions.Creator().create()
            )
        }
    }

    var shopId: String = ""
    var shopdata: Shop? = null
    fun setstartScan(){
        ScanUtil.startScan(
            this,
            REQUEST_CODE_SCAN_ONE,
            HmsScanAnalyzerOptions.Creator().create()
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SCAN_ONE) {
            if (data != null) {
                val obj: HmsScan? = data!!.getParcelableExtra(ScanUtil.RESULT)
                if (type == "1") {
                    mViewModel.prepare(shopId, 0, obj!!.originalValue)
                } else {
                    mViewModel.mttgprepare(obj!!.originalValue, shopId)
                }


                Log.d("yjk-----", obj!!.originalValue)
                return
            }
        }
    }

}