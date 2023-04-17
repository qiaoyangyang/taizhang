package com.meiling.oms.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.gyf.immersionbar.ImmersionBar
import com.meiling.common.fragment.BaseFragment
import com.meiling.oms.activity.VoucherInspectionActivity
import com.meiling.oms.databinding.FragmentScanBinding
import com.meiling.oms.dialog.MineExitDialog
import com.meiling.oms.viewmodel.FindViewModel
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast

class ScanFragment : BaseFragment<FindViewModel, FragmentScanBinding>() {

    companion object {
        fun newInstance() = ScanFragment()
    }


    override fun initView(savedInstanceState: Bundle?) {
        ImmersionBar.with(this).init()
        ImmersionBar.setTitleBar(this, mDatabind.TitleBar)
    }

    var type = ""
    override fun getBind(inflater: LayoutInflater): FragmentScanBinding {
        return FragmentScanBinding.inflate(inflater)
    }

    override fun initListener() {
        //抖音
        mDatabind.rlDouYin.setSingleClickListener {
            type = "1"
            mViewModel.cityshop(type)

        }
        mDatabind.rlKouBei.setSingleClickListener { showToast("功能暂未开通") }
        mDatabind.rlMeiTuan.setSingleClickListener {
            type = "2"
            mViewModel.cityshop(type)


        }

    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.shopBean.onStart.observe(this) {
            showLoading("")
        }
        mViewModel.shopBean.onSuccess.observe(this) {

            dismissLoading()
            if (it.isEmpty()) {
                val dialog: MineExitDialog =
                    MineExitDialog().newInstance(
                        "温馨提示", "暂无门店，请去「我的-->门店管理」中创建门店！",
                        "", "知道了", true
                    )
                dialog.setOkClickLister {
                    dialog.dismiss()


                }
                dialog.show(activity?.supportFragmentManager)
                return@observe
            }
            startActivity(
                Intent(mActivity, VoucherInspectionActivity::class.java).putExtra(
                    "type",
                    type
                )
            )

        }
        mViewModel.shopBean.onError.observe(this) {
            dismissLoading()
            showToast(it.msg)
        }
    }

}