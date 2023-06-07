package com.meiling.oms.dialog

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import com.meiling.common.network.data.DadaMerchantAddReq
import com.meiling.common.network.service.loginService
import com.meiling.common.utils.PermissionUtilis
import com.meiling.common.utils.RegularUtils
import com.meiling.common.view.ClearEditText
import com.meiling.oms.R
import com.meiling.oms.activity.BaseWebActivity
import com.meiling.oms.activity.NewOrderChangeAddressMapActivity
import com.meiling.oms.viewmodel.BindingLogisticsViewModel
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

    private var etStoreName: ClearEditText? = null
    private var etStoreTelephone: ClearEditText? = null
    private var etDetailedAddress: ClearEditText? = null
    private var etStoreNumber: ClearEditText? = null
    private var tvOperateType2: TextView? = null


    private var categoryId: String?=""
    private var lon: String=""
    private var lat: String=""
    var shopName:String=""
    var _etStoreAddress=""
    var _etDetailedAddress=""
    var phone=""
    private var etStoreAddress: TextView?=null
    var sureOnclickListener:(()->Unit)?=null
    var dadaMerchantAddReq= DadaMerchantAddReq()
    fun setMySureOnclickListener(listener: ()->Unit){
        this.sureOnclickListener=listener
    }

    fun newInstance(shopName:String,
                    lon:String,
                    lat:String,
                    etStoreAddress:String,
                    etDetailedAddress:String,
                    phone:String,
                    poid:String): DialogRegistDadaLogistics{
        val args = Bundle()
        args.putString("shopName",shopName)
        args.putString("lon",lon)
        args.putString("lat",lat)
        args.putString("etStoreAddress",etStoreAddress)
        args.putString("etDetailedAddress",etDetailedAddress)
        args.putString("phone",phone)
        args.putString("poid",poid)
        val fragment = DialogRegistDadaLogistics()
        fragment.arguments = args
        return fragment
    }
    private val REQUEST_CODE = 1000
    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        shopName=arguments?.getString("shopName")?:""
        lon=arguments?.getString("lon")?:""
        lat=arguments?.getString("lat")?:""
        _etStoreAddress=arguments?.getString("etStoreAddress")?:""
        _etDetailedAddress=arguments?.getString("etDetailedAddress")?:""
        phone=arguments?.getString("phone")?:""

        var poid=arguments?.getString("poid")?:""


        etStoreAddress=holder?.getView<TextView>(R.id.et_Store_address)
        var checkBoxAgree=holder?.getView<CheckBox>(R.id.checkBoxAgree)
        var mViewModel= ViewModelProvider(requireActivity()).get(BindingLogisticsViewModel::class.java)
        etStoreName=holder?.getView<ClearEditText>(R.id.et_Store_name)
        //手机号
        etStoreTelephone=holder?.getView<ClearEditText>(R.id.et_Store_telephone)
        //经营类型
        tvOperateType2=holder?.getView<TextView>(R.id.tv_operate_type2)
        //详细地址
        etDetailedAddress=holder?.getView<ClearEditText>(R.id.et_detailed_address)
        //邮箱
        etStoreNumber=holder?.getView<ClearEditText>(R.id.et_Store_number)


        shopName?.let {
            etStoreName?.setText(it)
        }
        _etStoreAddress.let {
            etStoreAddress?.text=it
        }
        _etDetailedAddress.let{
            etDetailedAddress?.setText(it)
        }
        lon?.let {
            lon=it
        }
        lat?.let {
            lat=it
        }
        phone.let {
            etStoreTelephone?.setText(it)
        }

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
            var operateTypeListDialog=OperateTypeListDialog().newInstance(poid!!)
            operateTypeListDialog.setMySureOnclickListener {
                tvOperateType2?.text=it.categoryName
                categoryId=it.categoryId
            }
            operateTypeListDialog.show(this.childFragmentManager)
        }

        var registLogisticsTitle=holder?.getView<TextView>(R.id.registLogisticsTitle)
        registLogisticsTitle?.setOnClickListener {
            dismiss()
        }

        var registUrl=holder?.getView<TextView>(R.id.registUrl)
        var userUrl =holder?.getView<TextView>(R.id.userUrl)
        var userUrl2 =holder?.getView<TextView>(R.id.userUrl2)
        registUrl?.setOnClickListener {
            startActivity(Intent(requireActivity(), BaseWebActivity::class.java)
                .putExtra("url", "https://kuai.imdada.cn/app/html/protocol/registry")
//                .putExtra("title","注册协议")
            )
        }
        userUrl?.setOnClickListener {
            startActivity(Intent(requireActivity(), BaseWebActivity::class.java)
                .putExtra("url", "https://kuai.imdada.cn/app/html/protocol/sensitive")
//                .putExtra("title","个人敏感信息授权协议")
            )
        }
        userUrl2?.setOnClickListener {
            startActivity(Intent(requireActivity(), BaseWebActivity::class.java)
                .putExtra("url", "https://page.imdada.cn/static/htmls/751/")
//                .putExtra("title","授权协议")
            )
        }

        var btn=holder?.getView<Button>(R.id.tv_go_on)
        btn?.canEnabled(etStoreName!!) { btnCanEnable() }
        btn?.canEnabled(etStoreTelephone!!) { btnCanEnable() }
        btn?.canEnabled(etStoreNumber!!) { btnCanEnable() }

        btn?.setOnClickListener {
            if(checkBoxAgree?.isChecked==false){
                showToast("请同意并勾选协议")
                return@setOnClickListener
            }
            if(!RegularUtils.isMobileSimple(etStoreTelephone?.text.toString().trim())){
                showToast("手机号格式错误,请修改")
                return@setOnClickListener
            }
            if(!RegularUtils.isEmail(etStoreNumber?.text.toString().trim())){
                showToast("请输入正确的邮箱")
                return@setOnClickListener
            }
            dadaMerchantAddReq.shopName=etStoreName?.text.toString()
            dadaMerchantAddReq.address=etStoreAddress?.text.toString()
            dadaMerchantAddReq.lat=lat
            dadaMerchantAddReq.lng=lon
            dadaMerchantAddReq.email=etStoreNumber?.text.toString()
            dadaMerchantAddReq.phone=etStoreTelephone?.text.toString()
            dadaMerchantAddReq.business=categoryId
            dadaMerchantAddReq.poiId=poid
            dadaMerchantAddReq.detailAddress=etDetailedAddress?.text.toString()

            mViewModel?.launchRequest(
                {
                    loginService.addMerChant(dadaMerchantAddReq)
                },
                onSuccess = {
                    showToast("操作成功")
                    dismiss()
                    sureOnclickListener?.invoke()
                },
                onError = {
                    it?.let { showToast(it) }
                }
            )

        }

    }

    private fun btnCanEnable():Boolean{
        var canEnable=!etStoreName?.text.toString().trim().isNullOrBlank()&&
                !etStoreAddress?.text.toString().trim().isNullOrBlank()&&
                !etStoreTelephone?.text.toString().trim().isNullOrBlank()&&
                !tvOperateType2?.text.toString().trim().isNullOrBlank()&&
                !etStoreNumber?.text.toString().trim().isNullOrBlank()
        return canEnable
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            // 处理返回的结果
            lon = data.getStringExtra("lon").toString()
            lat = data.getStringExtra("lat").toString()
            var address = data.getStringExtra("address").toString()
//            var poiItem = data.getParcelableExtra("poiItem",PoiItem::class.java)!!
//            var provinceCode = poiItem?.provinceCode!!
//            var adCode = poiItem?.adCode!!
//            var cityName = poiItem?.cityName!!
            etStoreAddress?.setText(address)
//            mDatabind.etStoreAddress.text = address
        }
    }

    fun Button.canEnabled(et: EditText, method:()->Boolean){
        val btn=this
        et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btn.isEnabled = method()
            }
        })
    }
}