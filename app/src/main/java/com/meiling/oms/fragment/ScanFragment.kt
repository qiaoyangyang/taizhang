package com.meiling.oms.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.gyf.immersionbar.ImmersionBar
import com.meiling.common.fragment.BaseFragment
import com.meiling.oms.activity.VoucherInspectionActivity
import com.meiling.oms.databinding.FragmentScanBinding
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

    override fun getBind(inflater: LayoutInflater): FragmentScanBinding {
        return FragmentScanBinding.inflate(inflater)
    }

    override fun initListener() {
        //抖音
        mDatabind.rlDouYin.setSingleClickListener {
            startActivity(
                Intent(mActivity, VoucherInspectionActivity::class.java).putExtra(
                    "type",
                    "1"
                )
            )
        }
        mDatabind.rlKouBei.setSingleClickListener { showToast("功能暂未开通") }
        mDatabind.rlMeiTuan.setSingleClickListener {
            startActivity(
                Intent(mActivity, VoucherInspectionActivity::class.java).putExtra(
                    "type",
                    "2"
                )
            )

        }

    }

}