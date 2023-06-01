package com.meiling.oms.dialog

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.amap.api.fence.PoiItem
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import com.meiling.common.utils.PermissionUtilis
import com.meiling.oms.R
import com.meiling.oms.activity.NewOrderChangeAddressMapActivity
import com.meiling.oms.widget.showToast
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder

/**
 * 达达物流注册
 */
class DialogRegistDadaLogistics : BaseNiceDialog() {
    init {
        setGravity(Gravity.BOTTOM)
        setOutCancel(false)
        setHeight(500)
    }
    override fun intLayoutId(): Int {
        return R.layout.dialog_regist_dada_logistics
    }

    private var etStoreAddress: TextView?=null
    var sureOnclickListener:(()->Unit)?=null
    fun setMySureOnclickListener(listener: ()->Unit){
        this.sureOnclickListener=listener
    }

    fun newInstance(title:String,tips:String): DialogRegistDadaLogistics{
        val args = Bundle()
        args.putString("title",title)
        args.putString("tips",tips)
        val fragment = DialogRegistDadaLogistics()
        fragment.arguments = args
        return fragment
    }
    private val REQUEST_CODE = 1000
    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        var title=arguments?.getString("title")
        var tips=arguments?.getString("tips")
        etStoreAddress=holder?.getView<TextView>(R.id.et_Store_address)
        var tvOperateType2=holder?.getView<TextView>(R.id.tv_operate_type2)
        etStoreAddress?.setOnClickListener {
            XXPermissions.with(this).permission(PermissionUtilis.Group.LOCAL)
                .request(object : OnPermissionCallback {
                    override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                        if (!allGranted) {
                            showToast("获取部分权限成功，但部分权限未正常授予")
                            return
                        }

                        startActivityForResult(Intent(requireActivity(),
                            NewOrderChangeAddressMapActivity::class.java),REQUEST_CODE)
                    }
                    override fun onDenied(
                        permissions: MutableList<String>,
                        doNotAskAgain: Boolean
                    ) {
                        if (doNotAskAgain) {
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(
                                requireActivity(),
                                permissions
                            )
                        } else {
                            showToast("授权失败，请检查权限")
                        }
                    }
                })
        }
        tvOperateType2?.setOnClickListener {
            var operateTypeListDialog=OperateTypeListDialog()
            operateTypeListDialog.show(this.childFragmentManager)
        }

        var registLogisticsTitle=holder?.getView<TextView>(R.id.registLogisticsTitle)
        registLogisticsTitle?.setOnClickListener {
            dismiss()
        }

        var btn=holder?.getView<Button>(R.id.tv_go_on)
        btn?.setOnClickListener {
            dismiss()
            sureOnclickListener?.invoke()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            // 处理返回的结果
            var lon = data.getStringExtra("lon").toString()
            var lat = data.getStringExtra("lat").toString()
            var address = data.getStringExtra("address").toString()
//            var poiItem = data.getParcelableExtra("poiItem",PoiItem::class.java)!!
//            var provinceCode = poiItem?.provinceCode!!
//            var adCode = poiItem?.adCode!!
//            var cityName = poiItem?.cityName!!
            etStoreAddress?.setText(address)
//            mDatabind.etStoreAddress.text = address
        }
    }
}