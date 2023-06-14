package com.meiling.account.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.gyf.immersionbar.ImmersionBar
import com.meiling.common.fragment.BaseFragment
import com.meiling.account.activity.ChannelActivity
import com.meiling.account.activity.VoucherInspectionActivity
import com.meiling.account.databinding.FragmentScanBinding
import com.meiling.account.dialog.MineExitDialog
import com.meiling.account.viewmodel.FindViewModel
import com.meiling.account.widget.setSingleClickListener
import com.meiling.account.widget.showToast

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
                        "温馨提示", "您还没有绑定该渠道的店铺\n" +
                                "在「我的-->渠道店铺」中可以绑定店铺\n",
                        "知道了", "去绑定", false
                    )
                dialog.setOkClickLister {
                    dialog.dismiss()
                    startActivity(Intent(requireActivity(), ChannelActivity::class.java).putExtra("type",type))


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