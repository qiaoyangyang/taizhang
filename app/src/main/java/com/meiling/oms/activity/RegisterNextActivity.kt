package com.meiling.oms.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.Toast
import com.blankj.utilcode.util.ActivityUtils
import com.gyf.immersionbar.ImmersionBar
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.meiling.common.activity.BaseVmActivity
import com.meiling.common.network.data.CancelOrderSend
import com.meiling.common.network.data.Children
import com.meiling.common.network.service.loginService
import com.meiling.common.utils.GlideAppUtils
import com.meiling.common.utils.GlideEngine
import com.meiling.common.view.ArrowPopupWindow
import com.meiling.common.view.ArrowTiedPopupWindow
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityRegisterNextBinding
import com.meiling.oms.dialog.MineExitDialog
import com.meiling.oms.dialog.SelectIndustryShopDialog
import com.meiling.oms.viewmodel.RegisterViewModel
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast
import com.wayne.constraintradiogroup.ConstraintRadioGroup
import okhttp3.MediaType
import okhttp3.MediaType.Companion.parse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class RegisterNextActivity : BaseVmActivity<RegisterViewModel>() {

    lateinit var mDatabind: ActivityRegisterNextBinding
    var phone: String? = ""
    override fun initView(savedInstanceState: Bundle?) {
           ImmersionBar.setTitleBar(this, mDatabind.TitleBar)
    }

    override fun onLeftClick(view: View) {
        val dialog: MineExitDialog =
            MineExitDialog().newInstance("温馨提示", "确定退出当前页面吗？", "取消", "确认", false)
        dialog.setOkClickLister {
            dialog.dismiss()
            startActivity(Intent(this,LoginActivity::class.java))
            ActivityUtils.finishAllActivities()
        }
        dialog.show(supportFragmentManager)
    }
    override fun onBackPressed() {
        val dialog: MineExitDialog =
            MineExitDialog().newInstance("温馨提示", "确定退出当前页面吗？", "取消", "确认", false)
        dialog.setOkClickLister {
            dialog.dismiss()
            startActivity(Intent(this,LoginActivity::class.java))
            ActivityUtils.finishAllActivities()
        }
        dialog.show(supportFragmentManager)
    }

    override fun initDataBind() {
        super.initDataBind()
        mDatabind = ActivityRegisterNextBinding.inflate(layoutInflater)
        setContentView(mDatabind.root)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun initData() {
        super.initData()
        phone = intent?.getStringExtra("phone")
        mDatabind.viewModel = mViewModel
        mViewModel.businessDto.value!!.userName=phone
        //选择所属行业
        mDatabind.txtIndustryRight.setOnClickListener {
            mViewModel.launchRequest(
                {
                    loginService.getCategory()
                },
                onSuccess = {
                    var list = it?.toMutableList()
                    var selectIndustryShopDialog =
                        SelectIndustryShopDialog().newInstance(list as ArrayList<Children>)
                    selectIndustryShopDialog.setOnresilience(object :
                        SelectIndustryShopDialog.Onresilience {
                        override fun resilience(
                            cityid: Int,
                            cityidname: String,
                            shopid: Int,
                            children: Children,
                        ) {
                            mDatabind.txtIndustryRight.text = children.name
                            mViewModel.businessDto.value!!.businessCategory = children.id
                        }

                        override fun Ondismiss() {
                        }

                    })
                    selectIndustryShopDialog.show(supportFragmentManager)
                },
                onError = {}
            )
        }
        mDatabind.txtAddImg.setOnClickListener {
            mDatabind.addImg.visibility=View.GONE
            mDatabind.addImg2.visibility=View.VISIBLE
            mDatabind.closeImg.visibility=View.GONE
            mDatabind.closeImg2.visibility=View.VISIBLE
            mViewModel.businessDto.value!!.logo = "https://static.igoodsale.com/default-logo-header.png"
        }

        mDatabind.closeImg.setOnClickListener {
            GlideAppUtils.loadResUrl(mDatabind.addImg, R.mipmap.addimg)
            mDatabind.addImg.visibility=View.VISIBLE
            mDatabind.addImg2.visibility=View.GONE
            mDatabind.closeImg.visibility=View.GONE
            mDatabind.closeImg2.visibility=View.GONE
            mViewModel.businessDto.value!!.logo =""
        }
        mDatabind.closeImg2.setOnClickListener {
            GlideAppUtils.loadResUrl(mDatabind.addImg, R.mipmap.addimg)
            mDatabind.addImg.visibility=View.VISIBLE
            mDatabind.addImg2.visibility=View.GONE
            mDatabind.closeImg.visibility=View.GONE
            mDatabind.closeImg2.visibility=View.GONE
            mViewModel.businessDto.value!!.logo =""
        }

        //选择图片
        mDatabind.addImg.setOnClickListener {
            PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .imageEngine(GlideEngine.createGlideEngine())
                .maxSelectNum(1)
                .minSelectNum(1)
                .isCompress(true)

                .isReturnEmpty(true)
                .isDisplayOriginalSize(true)
                .isPreviewImage(true)
                .minimumCompressSize(2048)
                .cutOutQuality(90)
                .forResult(object : OnResultCallbackListener<LocalMedia> {
                    override fun onResult(result: MutableList<LocalMedia>?) {
                        if (result?.isNotEmpty() == true) {
                            GlideAppUtils.loadUrl(mDatabind.addImg, result.get(0).compressPath)

                            val file = File(result.get(0).compressPath)
                            val part = MultipartBody.Part.createFormData(
                                "file",
                                result.get(0).fileName, RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
                            )
                            mViewModel.launchRequest(
                                {
                                    loginService.upload("",part)
                                },
                                onSuccess = {
                                    showToast("上传成功")
                                    mDatabind.closeImg.visibility=View.VISIBLE
                                    mViewModel.businessDto.value!!.logo = it
                                },
                                onError = {
                                    showToast("上传失败")
                                }
                            )
                        }
                    }

                    override fun onCancel() {

                    }

                })
        }


        mDatabind.btnNext.falseBackground(mDatabind.edtShopName,{tenantType1()})
        mDatabind.btnNext.falseBackground(mDatabind.edtTenantHead,{tenantType1()})
        mDatabind.btnNext.falseBackground(mDatabind.editAdministratorsLoginName,{tenantType1()})

        //注册
        mDatabind.btnNext.setSingleClickListener(1000) {

            mViewModel.businessDto.value!!.phone = this@RegisterNextActivity.phone

            if (mViewModel.businessDto.value!!.enterpriseName.isNullOrBlank()) {
                showToast("企业名称未填写")
                return@setSingleClickListener
            }

            if (mViewModel.businessDto.value!!.logo.isNullOrBlank()) {
                showToast("品牌LOGO未上传")
                return@setSingleClickListener
            }
            if (mViewModel.businessDto.value!!.businessCategory.isNullOrBlank()) {
                showToast("所属行业未选择")
                return@setSingleClickListener
            }
            if (mViewModel.businessDto.value!!.tenantHead.isNullOrBlank()) {
                showToast("管理员姓名未填写")
                return@setSingleClickListener
            }
            if (mViewModel.businessDto.value!!.userName.isNullOrBlank()) {
                showToast("登录账号未填写")
                return@setSingleClickListener
            }
//            if (mViewModel.businessDto.value!!.password.isNullOrBlank()) {
//                showToast("登录密码未填写")
//                return@setSingleClickListener
//            }
//            if (mViewModel.businessDto.value!!.password?.trim().toString()
//                    .toString().length > 20 || mViewModel.businessDto.value!!.password?.trim().toString()
//                    .toString().length < 8
//            ) {
//                showToast("密码长度需要在8-20位字符之间")
//                return@setSingleClickListener
//            }
//            if (!isPasswordValid(mViewModel.businessDto.value!!.password?.trim().toString())) {
//                showToast("密码不能是纯数字/纯字母/纯字符")
//                return@setSingleClickListener
//            }

            //注册
            register()

//            //校验账户名
//            mViewModel.launchRequest(
//                {
//                    loginService.checkUserName(mViewModel.businessDto.value!!.userName!!)
//                }, onSuccess = {
//                    //校验品牌名
//                    checkTenantName()
//                }, onError = {
//                    it?.let { showToast(it) }
//                }
//            )

        }

    }

    fun tenantType1():Boolean{
        return !mViewModel.businessDto.value!!.enterpriseName.isNullOrBlank()
                &&!mViewModel.businessDto.value!!.logo.isNullOrBlank()
                &&!mViewModel.businessDto.value!!.businessCategory.isNullOrBlank()
                &&!mViewModel.businessDto.value!!.tenantHead.isNullOrBlank()
                &&!mViewModel.businessDto.value!!.userName.isNullOrBlank()
                &&!mViewModel.businessDto.value!!.password.isNullOrBlank()
    }
    fun tenantType2():Boolean{
        return  !mViewModel.businessDto.value!!.logo.isNullOrBlank()
                &&!mViewModel.businessDto.value!!.businessCategory.isNullOrBlank()
                &&!mViewModel.businessDto.value!!.tenantHead.isNullOrBlank()
                &&!mViewModel.businessDto.value!!.userName.isNullOrBlank()
                &&!mViewModel.businessDto.value!!.password.isNullOrBlank()
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

    private fun checkTenantName() {
//        mViewModel.launchRequest(
//            {
//                loginService.thanBrand(mViewModel.businessDto.value!!.tenantName!!)
//            }, onSuccess = {
//                //注册
//                register()
//            }, onError = {
//                it?.let { showToast(it) }
//            }
//        )
    }

    private fun register() {
        showLoading("")
        mViewModel.launchRequest(
            {
                loginService.save(mViewModel.businessDto.value!!)
            },
            onSuccess = {
                disLoading()
                //成功会返回组合id
//                startActivity(Intent(this,
//                    BindingLogisticsActivity::class.java)
//                    .putExtra("tenantId", it!!.tenantId)
//                    .putExtra("account",mViewModel.businessDto.value!!.userName?.trim().toString())
//                    .putExtra("pwd",mViewModel.businessDto.value!!.password?.trim().toString())
//                    .putExtra("name", mViewModel.businessDto.value!!.tenantName.toString()))
                startActivity(Intent(this,
                    NewlyBuiltStoreActivity::class.java)
                    .putExtra("tenantId",it!!.tenantId)
                    .putExtra("adminViewId",it!!.adminUserViewId)
                    .putExtra("fromIntent","regist")
                    .putExtra("account",phone)
                    .putExtra("pwd",mViewModel.businessDto.value!!.password?.trim().toString())
                    .putExtra("name", mViewModel.businessDto.value!!.enterpriseName.toString()))
            },
            onError = {
                disLoading()
                it?.let { showToast(it) }
            }
        )

    }

    override fun isLightMode(): Boolean {
        return false
    }

    override fun createObserver() {
    }


}

fun Button.falseBackground(et: EditText, method:()->Boolean){
    val btn=this
    et.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if(method()){
                btn.setBackgroundResource(R.drawable.login_btn_select_true)
            }else{
                btn.setBackgroundResource(R.drawable.login_btn_select_false)
            }
        }
    })
}