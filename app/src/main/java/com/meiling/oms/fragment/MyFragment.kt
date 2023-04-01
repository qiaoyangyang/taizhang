package com.meiling.oms.fragment

import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.launcher.ARouter
import com.gyf.immersionbar.ImmersionBar
import com.meiling.common.fragment.BaseFragment
import com.meiling.oms.databinding.FragmentMyBinding
import com.meiling.oms.dialog.MineExitDialog
import com.meiling.oms.viewmodel.MyViewModel
import com.meiling.oms.widget.MMKVUtils
import com.meiling.oms.widget.setSingleClickListener

class MyFragment : BaseFragment<MyViewModel, FragmentMyBinding>() {

    companion object {
        fun newInstance() = MyFragment()
    }

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
    }

    override fun getBind(inflater: LayoutInflater): FragmentMyBinding {
        return FragmentMyBinding.inflate(inflater)
    }


    override fun initListener() {
        mDatabind.txtRecharge.setSingleClickListener {
            ARouter.getInstance().build("/app/MyRechargeActivity").navigation()
        }
        mDatabind.txtExit.setSingleClickListener {
            val dialog: MineExitDialog = MineExitDialog().newInstance("温馨提示", "确认退出当前账号吗？", false)
            dialog.setOkClickLister {
                MMKVUtils.clear()
                ARouter.getInstance().build("/app/LoginActivity").navigation()
            }
            dialog.show(childFragmentManager)
        }

        mDatabind.txtRemoveAccount.setSingleClickListener {
            val dialog: MineExitDialog =
                MineExitDialog().newInstance("温馨提示", "注销后，该账号将不可用。\n 请确认操作～", false)
            dialog.setOkClickLister {
                MMKVUtils.clear()
                ARouter.getInstance().build("/app/LoginActivity").navigation()
            }
            dialog.show(childFragmentManager)
        }
        mDatabind.txtModifyPwd.setSingleClickListener {
            ARouter.getInstance().build("/app/ModifyPassWordActivity").navigation()
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

}