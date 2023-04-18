package com.meiling.oms.activity

import android.os.Bundle
import android.util.Log
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
import com.meiling.oms.databinding.ActivityRegisterNextBinding
import com.meiling.oms.dialog.SelectIndustryShopDialog
import com.meiling.oms.viewmodel.RegisterViewModel
import com.meiling.oms.widget.showToast
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class RegisterNextActivity : BaseVmActivity<RegisterViewModel>() {

    lateinit var mDatabind: ActivityRegisterNextBinding
    var phone:String?="18311137330"
    override fun initView(savedInstanceState: Bundle?) {
//        phone=savedInstanceState?.getString("phone","18311137330")
        ImmersionBar.setTitleBar(this, mDatabind.TitleBar)
    }

    override fun initDataBind() {
        super.initDataBind()
        mDatabind = ActivityRegisterNextBinding.inflate(layoutInflater)
        setContentView(mDatabind.root)
    }

    override fun initData() {
        super.initData()
        mDatabind.viewModel = mViewModel
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
                            mViewModel.businessDto.value!!.businessCategory=children.id
                        }

                        override fun Ondismiss() {
                        }

                    })
                    selectIndustryShopDialog.show(supportFragmentManager)
                },
                onError = {}
            )
        }
        //选择图片
        mDatabind.addImg.setOnClickListener {
            PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .imageEngine(GlideEngine.createGlideEngine())
                .maxSelectNum(1)
                .minSelectNum(1)
                .isReturnEmpty(true)
                .isDisplayOriginalSize(true)
                .isPreviewImage(true)
                .minimumCompressSize(2000)
                .cutOutQuality(90)
                .forResult(object : OnResultCallbackListener<LocalMedia> {
                    override fun onResult(result: MutableList<LocalMedia>?) {
                        if (result?.isNotEmpty() == true) {
                            GlideAppUtils.loadUrl(mDatabind.addImg, result.get(0).path)
                            val file = File(result.get(0).path)
                            val body: RequestBody =
                                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)

                            val multipartBody = MultipartBody.Builder()
                                .addFormDataPart("file", file.name, body)
                                .setType(MultipartBody.FORM)
                                .build()

                            mViewModel.launchRequest(
                                {
                                    loginService.upload(multipartBody.parts)
                                },
                                onSuccess = {
                                    showToast("上传成功")
                                    mViewModel.businessDto.value!!.logo=it
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
                  mViewModel.businessDto.value!!.salesChannel=  it!!.map { it.id }.toList().joinToString(",")
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
                    mViewModel.businessDto.value!!.city=it.map{it.id}.toList().joinToString(",")
                }
            },
            onError = {

            }
        )

        mDatabind.btnNext.setOnClickListener {
            mViewModel.businessDto.value!!.phone=this@RegisterNextActivity.phone
            Log.e("",mViewModel.businessDto.value.toString())
            mViewModel.launchRequest(
                { loginService.save(mViewModel.businessDto.value!!)},
                onSuccess = {

                },
                onError = {
                    it?.let { showToast(it) }
                }
            )
        }
    }

    override fun isLightMode(): Boolean {
        return false
    }

    override fun createObserver() {
    }


}
