package com.meiling.oms.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.Nullable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.amap.api.location.AMapLocationClient
import com.codbking.widget.DatePickDialog
import com.codbking.widget.bean.DateType
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import com.meiling.common.activity.BaseActivity
import com.meiling.common.utils.PermissionUtilis
import com.meiling.oms.databinding.ActivityOrderChengeAddredssBinding
import com.meiling.oms.eventBusData.MessageEvent
import com.meiling.oms.viewmodel.ChangeAddressModel
import com.meiling.oms.widget.formatCurrentDateToString
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast
import org.greenrobot.eventbus.EventBus
import java.util.*

/**
 * 此改地址
 * **/
@Route(path = "/app/OrderChangeAddressActivity")
class OrderChangeAddressActivity :
    BaseActivity<ChangeAddressModel, ActivityOrderChengeAddredssBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityOrderChengeAddredssBinding {
        return ActivityOrderChengeAddredssBinding.inflate(layoutInflater)
    }


    private var type = true

    var address = ""
    var lat = ""
    var lon = ""
    var addressDetail = ""
    var orderId = ""

    var index = 1
    override fun initData() {
        val intent = intent
        mDatabind.txtOrderChangeTime.text = intent.getStringExtra("receiveTime")
        mDatabind.txtOrderChangePhone.setText(intent.getStringExtra("receivePhone"))
        mDatabind.txtOrderChangeName.setText(intent.getStringExtra("receiveName"))
        address = intent.getStringExtra("receiveAddress").toString()
        index = intent.getIntExtra("index", 1)
        orderId = intent.getStringExtra("orderId").toString()
        lat = intent.getStringExtra("lat").toString()
        lon = intent.getStringExtra("lon").toString()

        if (address.isNotEmpty()) {
            if (address.contains("@@")) {
                val x = address.split("@@")
                mDatabind.txtOrderChangeAddress.text = x[0]
                mDatabind.edtOrderChangeAddressDetail.setText(x[1])
            } else {
                mDatabind.txtOrderChangeAddress.text = address
            }
        } else {
            mDatabind.txtOrderChangeAddress.text = address
        }
        mDatabind.edtOrderChangeRemark.setText(intent.getStringExtra("receiveRemark"))


        mDatabind.txtOrderChangeTime.setSingleClickListener {
//            var orderDisSelectTimeDialog = OrderDisSelectTimeDialog().newInstance()
//            orderDisSelectTimeDialog.show(supportFragmentManager)
            showDatePickDialog(DateType.TYPE_YMDHM)
        }

//        mViewModel.lon.onSuccess.value = mDatabind.txtOrderChangeAddress.text.toString()
//        mViewModel.lat.onSuccess.value = mDatabind.txtOrderChangeAddress.text.toString()
        mDatabind?.btnSaveChange?.setSingleClickListener {

            if (mDatabind.txtOrderChangeName.text.trim().toString().isBlank()) {
                showToast("请输入收货人")
                return@setSingleClickListener
            }
            if (mDatabind.txtOrderChangePhone.text.trim().toString().isBlank()) {
                showToast("请输入收货人电话")
                return@setSingleClickListener
            }

            mViewModel.changeAddress(
                orderId,
                mDatabind.txtOrderChangeName.text.trim().toString(),
                mDatabind.txtOrderChangePhone.text.trim().toString(),
                mDatabind.txtOrderChangeAddress.text.toString() + "@@" + mDatabind.edtOrderChangeAddressDetail.text.toString(),
                lat,
                lon,
                mDatabind.txtOrderChangeTime.text.trim().toString()
            )

            if (mDatabind.edtOrderChangeRemark.text.trim().toString().isNotEmpty()
            ) {
                mViewModel.updateRemark(
                    mDatabind.edtOrderChangeRemark.text.trim().toString(),
                    orderId.toString()
                )
            }
        }
    }
    private val REQUEST_CODE = 1000
    private val ACCESS_FINE_LOCATION = 1
    override fun initListener() {
        mDatabind.btnCancelChange.setOnClickListener { finish() }
        mDatabind.txtOrderChangeAddress.setSingleClickListener {
            XXPermissions.with(this).permission(PermissionUtilis.Group.LOCAL)
                .request(object : OnPermissionCallback {
                    override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                        if (!allGranted) {
                            showToast("获取部分权限成功，但部分权限未正常授予")
                            return
                        }
                        //initStart()
                        ARouter.getInstance().build("/app/NewOrderChangeAddressMapActivity")
                            .navigation(this@OrderChangeAddressActivity, REQUEST_CODE)
                    }
                    override fun onDenied(
                        permissions: MutableList<String>,
                        doNotAskAgain: Boolean
                    ) {
                        if (doNotAskAgain) {
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(
                                this@OrderChangeAddressActivity,
                                permissions
                            )
                        } else {
                            showToast("授权失败，请检查权限")
                        }
                    }
                })

//
//            if (ContextCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.CALL_PHONE
//                ) == PackageManager.PERMISSION_GRANTED
//            ) {
//                // 如果有权限
//                ARouter.getInstance().build("/app/OrderChangeAddressMapActivity")
//                    .navigation(this, REQUEST_CODE)
//            } else {
//                // 如果没有权限，申请权限
//                ActivityCompat.requestPermissions(
//                    this,
//                    arrayOf(
//                        Manifest.permission.ACCESS_FINE_LOCATION,
//                        Manifest.permission.CALL_PHONE
//                    ),
//                    ACCESS_FINE_LOCATION
//                )
//            }
        }
    }

    fun initStart() {
        // 如果有权限
        ARouter.getInstance().build("/app/NewOrderChangeAddressMapActivity")
            .navigation(this, REQUEST_CODE)

//        ARouter.getInstance().build("/app/OrderChangeAddressMapActivity")
//            .navigation(this, REQUEST_CODE)
    }

    override fun createObserver() {
        mViewModel.changeAddressSuccess.onStart.observe(this) {
            showLoading("正在请求。。。")
        }
        mViewModel.changeAddressSuccess.onSuccess.observe(this) {
            disLoading()
            showToast("修改成功")
            EventBus.getDefault().post(MessageEvent(index));
            finish()
        }
        mViewModel.changeAddressSuccess.onError.observe(this) {
            disLoading()
            showToast(it.msg)
        }

        mViewModel.recvAddr.onSuccess.observe(this) {
            address = it
            mDatabind.txtOrderChangeAddress.text = address
        }
        mViewModel.recvAddrDeatil.onSuccess.observe(this) {
            addressDetail = it
        }
        mViewModel.lat.onSuccess.observe(this) {
            lat = it
        }
        mViewModel.lon.onSuccess.observe(this) {
            lon = it
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // 处理返回的结果
            lon = data.getStringExtra("lon").toString()
            lat = data.getStringExtra("lat").toString()
            address = data.getStringExtra("address").toString()
            mDatabind.txtOrderChangeAddress.text = address
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @SuppressLint("SetTextI18n")
    private fun showDatePickDialog(
        type: DateType,
    ) {
        val dialog = DatePickDialog(this)
        //设置上下年分限制
        dialog.setYearLimt(2)
        dialog.setStartDate(Date(System.currentTimeMillis()))
        //设置标题
        dialog.setTitle("选择时间")
        //设置类型
        dialog.setType(type)
        //设置消息体的显示格式，日期格式
        dialog.setMessageFormat("yyyy-MM-dd hh:mm")
        //设置选择回调
        dialog.setOnChangeLisener(null)
        //设置点击确定按钮回调
        dialog.setOnSureLisener { date ->
            // TODO: 时间校验
//            if (isSelectTimeCompare("${formatCurrentDateToString(date)}:00")){
//                mDatabind.txtOrderChangeTime.text = "${formatCurrentDateToString(date)}:00"
//            }else{
//                showToast("请选择正确时间")
//            }

            mDatabind.txtOrderChangeTime.text = "${formatCurrentDateToString(date)}:00"
        }
        dialog.show()
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == ACCESS_FINE_LOCATION) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // 如果有权限跳转
//                ARouter.getInstance().build("/app/OrderChangeAddressMapActivity")
//                    .navigation(this, REQUEST_CODE)
//            } else {
//                showToast("您已经禁止权限，请手动开启")
//                // 如果用户拒绝了权限，可以在这里处理相应的逻辑
//            }
//        }
//    }


}