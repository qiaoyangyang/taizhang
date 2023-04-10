package com.meiling.oms.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.Nullable
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.databinding.ActivityOrderChengeAddredssBinding
import com.meiling.oms.viewmodel.ChangeAddressModel
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast

/**
 * 此改地址
 * **/
@Route(path = "/app/OrderChangeAddressActivity")
class OrderChangeAddressActivity :
    BaseActivity<ChangeAddressModel, ActivityOrderChengeAddredssBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        setBar(this, mDatabind.cosChangeOrder)
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
    override fun initData() {
        val intent = intent
        mDatabind.txtOrderChangeTime.text = intent.getStringExtra("receiveTime")
        mDatabind.txtOrderChangePhone.setText(intent.getStringExtra("receivePhone"))
        mDatabind.txtOrderChangeName.setText(intent.getStringExtra("receiveName"))
        address = intent.getStringExtra("receiveAddress").toString()
        orderId = intent.getStringExtra("orderId").toString()
        showToast("order${orderId}")
        lat = intent.getStringExtra("lat").toString()
        lon = intent.getStringExtra("lon").toString()
        if (address!!.isBlank() && address!!.contains("@@")) {
            val x = address.split("@@")
            mDatabind.txtOrderChangeAddress.text = x[0]
            mDatabind.edtOrderChangeAddressDetail.setText(x[0])
            println(x[1])
        } else {
            mDatabind.txtOrderChangeAddress.text = address
        }
        mDatabind.edtOrderChangeRemark.setText(intent.getStringExtra("receiveRemark"))


//        mViewModel.lon.onSuccess.value = mDatabind.txtOrderChangeAddress.text.toString()
//        mViewModel.lat.onSuccess.value = mDatabind.txtOrderChangeAddress.text.toString()
        mDatabind?.btnSaveChange?.setSingleClickListener {

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
    override fun initListener() {
        mDatabind.aivBack.setOnClickListener { finish() }
        mDatabind.btnCancelChange.setOnClickListener { finish() }

        mDatabind.txtOrderChangeAddress.setSingleClickListener {
            ARouter.getInstance().build("/app/OrderChangeAddressMapActivity")
                .navigation(this, REQUEST_CODE)
        }


    }

    override fun createObserver() {
        mViewModel.changeAddressSuccess.onStart.observe(this) {
            showLoading("正在请求。。。")
        }
        mViewModel.changeAddressSuccess.onSuccess.observe(this) {
            disLoading()
            showToast("修改成功")
            finish()
        }
        mViewModel.changeAddressSuccess.onError.observe(this) {
            disLoading()
            showToast(it.msg)
        }

        mViewModel.recvAddr.onSuccess.observe(this) {
            address = it
            mDatabind.txtOrderChangeAddress.text = address
            showToast("===lon=======${it}")
        }
        mViewModel.recvAddrDeatil.onSuccess.observe(this) {
            addressDetail = it
            showToast("===lon=======${it}")
        }
        mViewModel.lat.onSuccess.observe(this) {
            lat = it
            showToast("===lon=======${it}")
        }
        mViewModel.lon.onSuccess.observe(this) {
            lon = it
            showToast("===lon=======${it}")
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

}