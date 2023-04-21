package com.meiling.oms.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.launcher.ARouter
import com.gyf.immersionbar.ImmersionBar
import com.meiling.common.constant.SPConstants
import com.meiling.common.fragment.BaseFragment
import com.meiling.common.utils.MMKVUtils
import com.meiling.oms.activity.BaseWebActivity
import com.meiling.oms.activity.ChannelActivity
import com.meiling.oms.activity.StoreManagementActivity
import com.meiling.oms.databinding.FragmentMyBinding
import com.meiling.oms.dialog.MineExitDialog
import com.meiling.oms.viewmodel.MyViewModel
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast

class MyFragment : BaseFragment<MyViewModel, FragmentMyBinding>() {

    companion object {
        fun newInstance() = MyFragment()
    }

    @SuppressLint("SetTextI18n")
    override fun initView(savedInstanceState: Bundle?) {
//        val fragments: MutableList<Fragment> = arrayListOf()
//        fragments.add(
//            SearchFragment.newInstance(R.mipmap.img_1244)
//        )
//        fragments.add(
//            SearchFragment.newInstance(R.mipmap.img_1296)
//        )
//        fragments.add(
//            SearchFragment.newInstance(R.mipmap.img_1152)
//        )
//        fragments.add(
//            SearchFragment.newInstance(R.mipmap.img_1148)
//        )
//        mDatabind.viewPager.adapter =
//            BaseFragmentPagerAdapter(childFragmentManager, lifecycle, fragments)
//        ViewPager2Delegate.install(mDatabind.viewPager, mDatabind.tabLayout)
        ImmersionBar.with(this).init()
        ImmersionBar.setTitleBar(this, mDatabind.clMy)
        mDatabind.txtNickName.text = MMKVUtils.getString(SPConstants.NICK_NAME)
        mDatabind.txtPhone.text = "${
            if (MMKVUtils.getInt(SPConstants.ROLE) == 1) {
                "管理员"
            } else {
                "店员"
            }
        }${MMKVUtils.getString(SPConstants.PHONE)}"


    }

    override fun getBind(inflater: LayoutInflater): FragmentMyBinding {
        return FragmentMyBinding.inflate(inflater)
    }


    override fun initListener() {
        mDatabind.txtChannel.setSingleClickListener {
            mViewModel.citypoi()
            //startActivity(Intent(requireActivity(), BaseWebActivity::class.java).putExtra("url","http://dev-oms.igoodsale.com/#/userAgreement/"))
            //
        }
        mDatabind.txtStoreManagement.setSingleClickListener {
            startActivity(Intent(requireActivity(), StoreManagementActivity::class.java))
        }
        mDatabind.txtRecharge.setSingleClickListener {
            ARouter.getInstance().build("/app/MyRechargeActivity").navigation()
        }
        mDatabind.txtExit.setSingleClickListener {
            val dialog: MineExitDialog =
                MineExitDialog().newInstance("温馨提示", "确认退出当前账号吗？", "取消", "确认", false)
            dialog.setOkClickLister {
                MMKVUtils.clear()
                ARouter.getInstance().build("/app/LoginActivity").navigation()
                requireActivity().finish()
            }
            dialog.show(childFragmentManager)
        }

        mDatabind.txtRemoveAccount.setSingleClickListener {
            val dialog: MineExitDialog =
                MineExitDialog().newInstance("温馨提示", "注销后，该账号将不可用。\n 请确认操作～", "取消", "确认", false)
            dialog.setOkClickLister {

                mViewModel.disableAccount(MMKVUtils.getString(SPConstants.adminViewId))

            }
            dialog.show(childFragmentManager)
        }
        mDatabind.txtModifyPwd.setSingleClickListener {
            ARouter.getInstance().build("/app/ModifyPassWordActivity").navigation()
        }
        mDatabind.txtMsgCenter.setSingleClickListener {
            ARouter.getInstance().build("/app/MessageCenterActivity").navigation()
        }
        mDatabind.txtAbout.setSingleClickListener {
            ARouter.getInstance().build("/app/AboutActivity").navigation()
        }
//        mDatabind.aivMore.setOnClickListener {
////            mDatabind.drawerLayout.openDrawer(GravityCompat.START)
//        }
//        mDatabind.aivShare.setOnClickListener {
//            ARouter.getInstance().build("/app/ShareActivity").navigation()
//        }
//        mDatabind.aivModify.setOnClickListener {
//            ARouter.getInstance().build("/app/ModifyInformationActivity").navigation()
//        }
    }

    override fun createObserver() {
        mViewModel.disableAccountDto.onStart.observe(this) {
            showLoading("正在请求")
        }
        mViewModel.disableAccountDto.onSuccess.observe(this) {
            dismissLoading()
            MMKVUtils.clear()
            ARouter.getInstance().build("/app/LoginActivity").navigation()
            requireActivity().finish()
        }
        mViewModel.disableAccountDto.onError.observe(this) {
            dismissLoading()
            showToast(it.msg)
        }

        mViewModel.shopBean.onStart.observe(this) {
            showLoading()
        }
        mViewModel.shopBean.onSuccess.observe(this) {
            dismissLoading()
            if (it.size == 0) {
                startActivity(Intent(requireActivity(), StoreManagementActivity::class.java))
            } else {
                startActivity(Intent(requireActivity(), ChannelActivity::class.java))
            }
        }
        mViewModel.shopBean.onError.observe(this) {
            dismissLoading()
            showToast(it.msg)
        }

    }

}