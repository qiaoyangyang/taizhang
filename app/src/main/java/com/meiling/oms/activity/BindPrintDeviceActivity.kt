package com.meiling.oms.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import com.huawei.hms.hmsscankit.ScanKitActivity
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsScan
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions
import com.meiling.common.activity.BaseVmDbActivity
import com.meiling.common.network.data.CityPoiDto
import com.meiling.common.network.data.PrinterConfigDto
import com.meiling.common.network.data.ShopPoiDto
import com.meiling.common.utils.PermissionUtilis
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityBindPrintDeviceBinding
import com.meiling.oms.dialog.AccountSelectCityDialog
import com.meiling.oms.dialog.AccountSelectShopOrCityDialog
import com.meiling.oms.dialog.MineExitDialog
import com.meiling.oms.dialog.SelectPrintPageSizeDialog
import com.meiling.oms.viewmodel.RegisterViewModel
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast

/**
 * 绑定打印机页面
 */
class BindPrintDeviceActivity :
    BaseVmDbActivity<RegisterViewModel, ActivityBindPrintDeviceBinding>() {
    var printNum = "1"
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.imgReduce.setOnClickListener {
            var printNum = mDatabind.txtPrintPageNum2.text.toString().toInt()
            if (printNum == 1) {
                return@setOnClickListener
            }


            var i = printNum - 1
            mDatabind.txtPrintPageNum2.text = i.toString()

        }
        mDatabind.imgAdd.setOnClickListener {
            var printNum = mDatabind.txtPrintPageNum2.text.toString().toInt()
            if (printNum == 5) {
                return@setOnClickListener
            }


            var i = printNum + 1
            mDatabind.txtPrintPageNum2.text = i.toString()

        }
        mDatabind.txtPrintPageNum2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                isadd(mDatabind.txtPrintPageNum2?.text.toString(), mDatabind.imgAdd, "5")
            }

        })
    }

    override fun createObserver() {

        mViewModel.printDetail.onSuccess.observe(this) {
            mDatabind.txtPinPai2.text = it.brandCodeName//品牌
            mDatabind.editPrintName.setText(it?.name)//打印机名称
            mDatabind.edtPrintNo.setText(it?.deviceid.toString())//打印机编号
            mDatabind.txtPrintPageNum2.text = it?.printNum.toString()//打印张数
            mDatabind.checkPrintAuto.setChecked(it.autoPrint == 1)
            mDatabind.checkRefundAuto.setChecked(it.tkAutoPrint == 1)
            mDatabind.checkChangeOrderPrintAuto.setChecked(it.updateAutoPrint == 1)
            if (it.type == 1) {
                type = 1
                mDatabind.txtPrintWidth2.text = "58mm"//打印机编号
            } else if (it.type == 2) {
                type = 2
                mDatabind.txtPrintWidth2.text = "80mm"//打印机编号
            }
            isSelect = it.poiList.size.toString()
            if (it?.flag==true){
                mDatabind.selectShop.text="全部门店"
            }else {
                mDatabind.selectShop.text="已选择${isSelect}个门店"
            }

            it.poiList.forEach { id ->
                poiIds.add(id)
                shopPoiDtoList.add(ShopPoiDto(id))
            }
            isadd(mDatabind.txtPrintPageNum?.text.toString(), mDatabind.imgAdd, "5")
        }

        mViewModel.getPoiListsize.onSuccess.observe(this) { it ->
            isSelect = it?.content?.size.toString()
            it.content?.forEach {
                poiIds.add(it?.id.toString())
            }
        }
        mViewModel.delDev.onSuccess.observe(this) {
            disLoading()
            startActivity(Intent(this, PrintDeviceListActivity::class.java))
        }
        mViewModel.update.onSuccess.observe(this) {
            startActivity(Intent(this, PrintDeviceListActivity::class.java))
        }
        mViewModel.delDev.onStart.observe(this) {
            showLoading("")


        }
        mViewModel.delDev.onError.observe(this) {
            disLoading()
            showToast(it.msg)
        }
        mViewModel.update.onStart.observe(this) {
            showLoading("")


        }
        mViewModel.update.onError.observe(this) {
            disLoading()
            showToast(it.msg)
        }


    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityBindPrintDeviceBinding {
        return ActivityBindPrintDeviceBinding.inflate(layoutInflater)
    }

    var cityPoiDtoList = ArrayList<CityPoiDto>()
    var shopPoiDtoList = ArrayList<ShopPoiDto>()
    var poiIds = ArrayList<String>()
    var isSelect = "0"
    var iscompile = ""//

    override fun initData() {
        super.initData()

        var name = intent.getStringExtra("name")
        var deviceid = intent.getStringExtra("deviceid").toString()
        if (TextUtils.isEmpty(name)) {
            iscompile = "bianji"
            mDatabind.scanLin.visibility = View.GONE
            mDatabind.edtPrintNo.keyListener = null
            mViewModel.printDetail(deviceid)


        } else {
            mViewModel.getPoiList()
            mDatabind.txtPinPai2.text = name
            mDatabind.editPrintName.setText(name)
            isadd(mDatabind.txtPrintPageNum?.text.toString(), mDatabind.imgAdd, "5")

        }
        mDatabind.btnLogin.setOnClickListener {
            if (TextUtils.isEmpty(mDatabind.editPrintName.text.toString())) {
                showToast("输入打印机名称")
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(mDatabind.edtPrintNo.text.toString())) {
                showToast("请输入终端编号/SN号")
                return@setOnClickListener
            }

            var printerConfigDto = PrinterConfigDto()

            if (mDatabind.checkPrintAuto.isChecked()) {
                printerConfigDto.autoPrint = 1
            } else {
                printerConfigDto.autoPrint = 0
            }
            if (mDatabind.txtPinPai2.text.toString() == "芯烨云") {
                printerConfigDto.brandCode = "xinye"
            } else if (mDatabind.txtPinPai2.text.toString() == "佳博云") {
                printerConfigDto.brandCode = "jiabo"
            } else if (mDatabind.txtPinPai2.text.toString() == "sunmi") {
                printerConfigDto.brandCode = "商米"
            }

            printerConfigDto.deviceid = mDatabind.edtPrintNo.text.toString()
            printerConfigDto.name = mDatabind.editPrintName.text.toString()
            printerConfigDto.printNum = mDatabind.txtPrintPageNum2.text.toString().toInt()
            if (mDatabind.checkRefundAuto.isChecked()) {
                printerConfigDto.tkAutoPrint = 1
            } else {
                printerConfigDto.tkAutoPrint = 0
            }
            if (mDatabind.checkChangeOrderPrintAuto.isChecked()) {
                printerConfigDto.updateAutoPrint = 1
            } else {
                printerConfigDto.updateAutoPrint = 0
            }
            printerConfigDto.poiIds = poiIds

            printerConfigDto.type = type

            if (TextUtils.isEmpty(name)) {
                mViewModel.update(printerConfigDto)
            } else {


                mViewModel.addDev(printerConfigDto)
            }
        }
        //扫码识别
        mDatabind.scanLin.setOnClickListener {
            // startActivity(Intent(this, ScanKitActivity::class.java))
            XXPermissions.with(this).permission(PermissionUtilis.Group.RICHSCAN)
                .request(object : OnPermissionCallback {
                    override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                        if (!allGranted) {
                            showToast("获取部分权限成功，但部分权限未正常授予")
                            return
                        }
                        setstartScan()

                    }

                    override fun onDenied(
                        permissions: MutableList<String>,
                        doNotAskAgain: Boolean
                    ) {
                        if (doNotAskAgain) {
                            showToast("被永久拒绝授权，请手动授予录音和日历权限")
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(
                                this@BindPrintDeviceActivity,
                                permissions
                            )
                        } else {
                            showToast("获取录音和日历权限失败")
                        }
                    }

                })
        }
        //关联发货门店
        mDatabind.selectShop.setSingleClickListener {

            var accountSelectDialog = AccountSelectShopOrCityDialog().newInstance(
                "授权发货门店",
                iscompile,
                isSelect,
                shopPoiDtoList,
                false
            )
            accountSelectDialog.show(supportFragmentManager)
            accountSelectDialog.setOkClickItemLister { arrayList, isSelectAll ->
                if (arrayList.size != 0) {

                    poiIds.clear()
                    arrayList.forEach {
                        poiIds.add(it?.poiIds.toString())

                    }
                    mDatabind.selectShop.text="已选择${arrayList.size}个门店"
                }else{
                    mDatabind.selectShop.text="全部门店"
                }

            }
        }
        //小票宽度
        mDatabind.txtPrintWidth2.setOnClickListener {

            var selectPrintPageSizeDialog = SelectPrintPageSizeDialog()
            selectPrintPageSizeDialog.setSelectPageClick { size, typ ->
                mDatabind.txtPrintWidth2.text = "${size}mm"
                type = typ
            }


            selectPrintPageSizeDialog.show(supportFragmentManager)
        }
    }

    val DECODE = 1
    var type = 1
    val CAMERA_REQ_CODE = 111;
    val REQUEST_CODE_SCAN_ONE = 0X01
    fun setstartScan() {
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
                mDatabind.edtPrintNo.setText(obj?.originalValue.toString())//打印机编号
                Log.d("yjk-----", obj!!.originalValue)
                return
            }
        }
    }

    fun isadd(S: String, textView: ImageView, count: String) {
        if (S == count) {
            textView.setBackgroundResource(R.drawable.jiad)
        } else {
            textView.setBackgroundResource(R.drawable.jiaz)
        }
    }


}
