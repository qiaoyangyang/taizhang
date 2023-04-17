package com.meiling.oms.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.meiling.common.activity.BaseActivity
import com.meiling.common.activity.BaseVmActivity
import com.meiling.common.activity.BaseVmDbActivity
import com.meiling.common.network.service.loginService
import com.meiling.common.utils.GlideAppUtils
import com.meiling.common.utils.GlideEngine
import com.meiling.common.utils.InputTextManager
import com.meiling.oms.databinding.ActivityRegisterBinding
import com.meiling.oms.databinding.ActivityRegisterNextBinding
import com.meiling.oms.viewmodel.RegisterViewModel
import com.meiling.oms.widget.CaptchaCountdownTool
import com.meiling.oms.widget.showToast

class RegisterNextActivity : BaseActivity<RegisterViewModel, ActivityRegisterNextBinding>() {

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {
        super.initData()


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
                .forResult(object :OnResultCallbackListener<LocalMedia>{
                    override fun onResult(result: MutableList<LocalMedia>?) {
                        if(result?.isNotEmpty() == true){
                            GlideAppUtils.loadUrl(mDatabind.addImg,result.get(0).path)
                        }
                    }

                    override fun onCancel() {

                    }

                })
        }



    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityRegisterNextBinding {
        return ActivityRegisterNextBinding.inflate(layoutInflater)
    }


}
