package com.meiling.oms.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import com.gyf.immersionbar.ImmersionBar
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.meiling.common.activity.BaseVmActivity
import com.meiling.common.network.data.Children
import com.meiling.common.network.service.loginService
import com.meiling.common.utils.GlideAppUtils
import com.meiling.common.utils.GlideEngine
import com.meiling.common.view.ArrowPopupWindow
import com.meiling.common.view.ArrowTiedPopupWindow
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityRegisterNextBinding
import com.meiling.oms.dialog.SelectIndustryShopDialog
import com.meiling.oms.viewmodel.RegisterViewModel
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
        mDatabind.tips1.setOnClickListener {
            var mpup = ArrowTiedPopupWindow(this@RegisterNextActivity)
            mpup.apply {
                setBackground(R.color.zxing_transparent, 5f, 20, 10)
                setArrow(R.color.black, 0.2f, ArrowPopupWindow.ArrowSize.BIGGER)
                setPopupView(layoutInflater.inflate(R.layout.pup_layout, null))
                setTiedView(mDatabind.tips1, ArrowTiedPopupWindow.TiedDirection.BOTTOM)
                preShow()
                isOutsideTouchable = true
                show()
            }
        }
        mDatabind.tips2.setOnClickListener {
            var mpup = ArrowTiedPopupWindow(this@RegisterNextActivity)
            mpup.apply {
                setBackground(R.color.zxing_transparent, 5f, 20, 10)
                setArrow(R.color.black, 0.6f, ArrowPopupWindow.ArrowSize.BIGGER)
                setPopupView(layoutInflater.inflate(R.layout.pup_layout2, null))
                setTiedView(mDatabind.tips2, ArrowTiedPopupWindow.TiedDirection.BOTTOM)
                preShow()
                isOutsideTouchable = true
                show()
            }
        }
        mDatabind.tips3.setOnClickListener {
            var mpup = ArrowTiedPopupWindow(this@RegisterNextActivity)
            mpup.apply {
                setBackground(R.color.zxing_transparent, 5f, 0, 10)
                setArrow(R.color.black, 1f, ArrowPopupWindow.ArrowSize.BIGGER)
                setPopupView(layoutInflater.inflate(R.layout.pup_layout3, null))
                setTiedView(mDatabind.tips3, ArrowTiedPopupWindow.TiedDirection.BOTTOM)
                preShow()
                isOutsideTouchable = true
                show()
            }
        }
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
                            mDatabind.txtIndustryRight.text = cityidname
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
            GlideAppUtils.loadResUrl(mDatabind.addImg, R.mipmap.default_logo)
            mViewModel.businessDto.value!!.logo = "https://static.igoodsale.com/default-logo-header.png"
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
        //获取销售渠道id
        mViewModel.launchRequest(
            { loginService.getChannel() },
            onSuccess = {
                it.let {
                    mViewModel.businessDto.value!!.salesChannel =
                        it!!.map { it.id }.toList().joinToString(",")
                }
            },
            onError = {

            }
        )
        //获取城市id
        mViewModel.launchRequest(
            { loginService.getCity() },
            onSuccess = {
                it?.let {
                    mViewModel.businessDto.value!!.city =
                        it.map { it.id }.toList().joinToString(",")
                }
            },
            onError = {

            }
        )

        mDatabind.shopTypeRG.checkedChangeListener =
            object : com.wayne.constraintradiogroup.OnCheckedChangeListener {
                override fun onCheckedChanged(
                    group: ConstraintRadioGroup,
                    checkedButton: CompoundButton,
                ) {
                    if (checkedButton.id == R.id.checkEnterprise) {
                        mDatabind.txtOperateType.visibility = View.VISIBLE
                        mDatabind.txtOperateTypeRed.visibility = View.VISIBLE
                        mDatabind.operateTypeRG.visibility = View.VISIBLE
                        mDatabind.line2.visibility = View.VISIBLE
                        mDatabind.line4.visibility = View.VISIBLE
                        mDatabind.txtShopName.visibility = View.VISIBLE
                        mDatabind.txtShopName2.visibility = View.VISIBLE
                        mDatabind.edtShopName.visibility = View.VISIBLE
                    }
                    if (checkedButton.id == R.id.checkPerson) {
                        mDatabind.txtOperateType.visibility = View.GONE
                        mDatabind.txtOperateTypeRed.visibility = View.GONE
                        mDatabind.operateTypeRG.visibility = View.GONE
                        mDatabind.line2.visibility = View.GONE
                        mDatabind.line4.visibility = View.GONE
                        mDatabind.txtShopName.visibility = View.GONE
                        mDatabind.txtShopName2.visibility = View.GONE
                        mDatabind.edtShopName.visibility = View.GONE
                        mViewModel.businessDto.value!!.isChain = ""
                        mViewModel.businessDto.value!!.enterpriseName = ""
                    }
                    if (checkedButton.id == R.id.checkOther) {
                        mDatabind.txtOperateType.visibility = View.VISIBLE
                        mDatabind.txtOperateTypeRed.visibility = View.VISIBLE
                        mDatabind.operateTypeRG.visibility = View.VISIBLE
                        mDatabind.line2.visibility = View.VISIBLE
                        mDatabind.line4.visibility = View.GONE
                        mDatabind.txtShopName.visibility = View.GONE
                        mDatabind.txtShopName2.visibility = View.GONE
                        mDatabind.edtShopName.visibility = View.GONE
                        mViewModel.businessDto.value!!.enterpriseName = ""
                    }

                }

            }

        //注册
        mDatabind.btnNext.setOnClickListener {
            mViewModel.businessDto.value!!.phone = this@RegisterNextActivity.phone

            if (mViewModel.businessDto.value!!.tenantType == "1") {
                if (mViewModel.businessDto.value!!.enterpriseName.isNullOrBlank()) {
                    showToast("企业名称未填写")
                    return@setOnClickListener
                }
            }
            if (mViewModel.businessDto.value!!.tenantName.isNullOrBlank()) {
                showToast("品牌名称未填写")
                return@setOnClickListener
            }
            if (mViewModel.businessDto.value!!.logo.isNullOrBlank()) {
                showToast("品牌LOGO未上传")
                return@setOnClickListener
            }
            if (mViewModel.businessDto.value!!.businessCategory.isNullOrBlank()) {
                showToast("所属行业未选择")
                return@setOnClickListener
            }
            if (mViewModel.businessDto.value!!.tenantHead.isNullOrBlank()) {
                showToast("管理员姓名未填写")
                return@setOnClickListener
            }
            if (mViewModel.businessDto.value!!.userName.isNullOrBlank()) {
                showToast("登录账号未填写")
                return@setOnClickListener
            }
            if (mViewModel.businessDto.value!!.password.isNullOrBlank()) {
                showToast("登录密码未填写")
                return@setOnClickListener
            }

            //校验账户名
            mViewModel.launchRequest(
                {
                    loginService.checkUserName(mViewModel.businessDto.value!!.userName!!)
                }, onSuccess = {
                    //校验品牌名
                    checkTenantName()
                }, onError = {
                    it?.let { showToast(it) }
                }
            )

        }

    }

    private fun checkTenantName() {
        mViewModel.launchRequest(
            {
                loginService.thanBrand(mViewModel.businessDto.value!!.tenantName!!)
            }, onSuccess = {
                //注册
                register()
            }, onError = {
                it?.let { showToast(it) }
            }
        )
    }

    private fun register() {
        showLoading("")

        mViewModel.launchRequest(
            { loginService.save(mViewModel.businessDto.value!!) },
            onSuccess = {
                disLoading()
                //成功会返回组合id
                startActivity(Intent(this,
                    BindingLogisticsActivity::class.java)
                    .putExtra("tenantId", it)
                    .putExtra("name", mViewModel.businessDto.value!!.tenantName.toString()))
//                startActivity(Intent(this, NewlyBuiltStoreActivity::class.java))
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
