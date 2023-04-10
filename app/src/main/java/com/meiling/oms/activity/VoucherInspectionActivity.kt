package com.meiling.oms.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.app.ActivityCompat
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsScan
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.Shop
import com.meiling.common.network.data.ShopBean
import com.meiling.common.network.data.ThrillBen
import com.meiling.common.utils.TextDrawableUtils
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityVoucherinspectionBinding
import com.meiling.oms.dialog.CheckCouponInformationDidalog
import com.meiling.oms.dialog.ShopDialog
import com.meiling.oms.viewmodel.VoucherinspectionViewModel


//验券
class VoucherInspectionActivity :
    BaseActivity<VoucherinspectionViewModel, ActivityVoucherinspectionBinding>() {

    var type = ""
    var thrillBen = ArrayList<ThrillBen>()
    var shopBean = ArrayList<ShopBean>()
    override fun initView(savedInstanceState: Bundle?) {
        TextDrawableUtils.setRightDrawable(mDatabind.TitleBar.titleView, R.drawable.xia)
        //验券历史
        mDatabind.tvVoucherInspectionHistory.setOnClickListener {
            startActivity(
                Intent(this, VoucherInspectionHistoryActivity::class.java).putExtra("shop",shopdata)
            )
        }
        //  输码验券
        mDatabind.tvInputBoredom.setOnClickListener {
            startActivity(
                Intent(this, InputBoredomActivity::class.java)
            )
        }


        mDatabind.clScan.setOnClickListener {
            requestPermission(CAMERA_REQ_CODE, DECODE)
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
            if (it.size!=0) {
                var shopDialog = ShopDialog().newInstance(it)

                shopDialog.setOnresilience(object : ShopDialog.Onresilience {
                    override fun resilience(cityid: Int, shopid: Int, shop: Shop) {
                        mViewModel.Shop.onSuccess.postValue(shop)
                        shopId = shop?.id.toString()
                        shopdata= shop
                        mDatabind.TitleBar.titleView.text = shop.name
                    }

                })
                shopDialog.show(supportFragmentManager)
            }

        }
    }

    override fun createObserver() {
        mViewModel.thrillBen.onSuccess.observe(this) {
            if (it.size!=0) {
                var checkCouponInformationDidalog = CheckCouponInformationDidalog().newInstance(it)
                checkCouponInformationDidalog.setOnresilience(object :CheckCouponInformationDidalog.Onresilience{
                    override fun resilience(encryptedCode: String) {
                        mViewModel.verify(shopId,encryptedCode)
                    }

                })

                checkCouponInformationDidalog.show(supportFragmentManager)
            }


        }
        mViewModel.shopBean.onSuccess.observe(this){
            shopdata= it[0].shopList?.get(0)
            shopId=it.get(0).shopList?.get(0)?.id!!
            mDatabind.TitleBar.titleView.text=it.get(0).shopList?.get(0)?.name
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
    var shopdata: Shop? =null


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SCAN_ONE) {
            if (data != null) {
                val obj: HmsScan? = data!!.getParcelableExtra(ScanUtil.RESULT)
                mViewModel.prepare(shopId, 0, obj!!.originalValue)


                Log.d("yjk-----", obj!!.originalValue)
                return
            }
        }
    }

}