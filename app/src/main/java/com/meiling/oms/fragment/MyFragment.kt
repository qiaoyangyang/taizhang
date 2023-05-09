package com.meiling.oms.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.Utils
import com.gyf.immersionbar.ImmersionBar
import com.meiling.common.constant.SPConstants
import com.meiling.common.fragment.BaseFragment
import com.meiling.common.network.data.ByTenantId
import com.meiling.common.utils.MMKVUtils
import com.meiling.oms.activity.*
import com.meiling.oms.databinding.FragmentMyBinding
import com.meiling.oms.dialog.MineExitDialog
import com.meiling.oms.dialog.OrderDialog
import com.meiling.oms.viewmodel.MainViewModel2
import com.meiling.oms.viewmodel.MyViewModel
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast

class MyFragment : BaseFragment<MyViewModel, FragmentMyBinding>() {

    companion object {
        fun newInstance() = MyFragment()
    }

    lateinit var vm: MainViewModel2

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

        vm = ViewModelProvider(
            MainActivity.mainActivity!!,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel2::class.java)


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

//        mDatabind.svHead.setOnClickListener {
//
//            OrderDialog().newInstance().show(childFragmentManager)
//
//        }
    }

    override fun getBind(inflater: LayoutInflater): FragmentMyBinding {
        return FragmentMyBinding.inflate(inflater)
    }

    override fun onResume() {
        super.onResume()
           mViewModel.getByTenantId()
    }


    override fun initListener() {
        //打印机配置
        mDatabind.llPrintBinding.setSingleClickListener {
            if(vm.getByTenantId.value?.poi==-1){
                //未创建门店
//                startActivity(Intent(requireActivity(), NoStoreActivity::class.java))
//                startActivity(Intent(requireActivity(), NoPrintDeviceActivity::class.java))
                startActivity(Intent(requireActivity(), PrintDeviceListActivity::class.java))

            }else{
                //未绑定打印机
//                startActivity(Intent(requireActivity(), NoPrintDeviceActivity::class.java))
                startActivity(Intent(requireActivity(), PrintDeviceListActivity::class.java))

                //物流是否绑定
//                if(vm.getByTenantId.value?.logistics==-1){
//                    startActivity(Intent(requireActivity(),BindingLogisticsActivity::class.java))
//                }else{
//                    startActivity(Intent(requireActivity(),BindingLogisticsActivity::class.java))
//                }
            }
        }
        //渠道店铺管理
        mDatabind.llChannel.setSingleClickListener {
            // mViewModel.citypoi()
            startActivity(Intent(requireActivity(), ChannelActivity::class.java))
        }
        //门店管理
        mDatabind.llStoreManagement.setSingleClickListener {
            startActivity(Intent(requireActivity(), StoreManagementActivity::class.java))
        }

        //物流绑定
        mDatabind.llLogisticsBinding.setOnClickListener {
            //门店是否创建
//            if(vm.getByTenantId.value?.poi==-1){
            startActivity(Intent(requireActivity(), NoStoreActivity::class.java))
//            }else{
//                //物流是否绑定
//                if(vm.getByTenantId.value?.logistics==-1){
//                    startActivity(Intent(requireActivity(),BindingLogisticsActivity::class.java))
//                }else{
//                    startActivity(Intent(requireActivity(),BindingLogisticsActivity::class.java))
//                }
//            }
        }
        mDatabind.txtRecharge.setSingleClickListener {
            ARouter.getInstance().build("/app/MyRechargeActivity").navigation()
        }
        mDatabind.txtAccountManager.setSingleClickListener {
            startActivity(Intent(requireActivity(), AccountManagerActivity::class.java))
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

        //通知设置
        mDatabind.txtNotificationSettings.setOnClickListener {
            startActivity(Intent(activity, NotificationSettingsActivity::class.java))
        }
    }

    override fun createObserver() {

        mViewModel.getByTenantId.onSuccess.observe(this) {
            SaveUserBean(it)
            if (it.logistics == 1) {//物流是否绑定 1绑定;-1没绑定
                mDatabind.tvIsLogisticsBinding.visibility = View.GONE
            } else {
                mDatabind.tvIsLogisticsBinding.visibility = View.VISIBLE
            }
            if (it.poi == 1) {//门店是否创建 1绑定;-1没绑定
                mDatabind.tvIsStoreManagement.visibility = View.GONE
            } else {
                mDatabind.tvIsStoreManagement.visibility = View.VISIBLE
            }
            if (it.shop == 1) {//渠道是否创建 1绑定;-1没绑定
                mDatabind.tvIschannel.visibility = View.GONE
            } else {
                mDatabind.tvIschannel.visibility = View.VISIBLE
            }
        }
        vm.getByTenantId.observe(this) {
            if (it.logistics == 1) {//物流是否绑定 1绑定;-1没绑定
                mDatabind.tvIsLogisticsBinding.visibility = View.GONE
            } else {
                mDatabind.tvIsLogisticsBinding.visibility = View.VISIBLE
            }
            if (it.poi == 1) {//门店是否创建 1绑定;-1没绑定
                mDatabind.tvIsStoreManagement.visibility = View.GONE
            } else {
                mDatabind.tvIsStoreManagement.visibility = View.VISIBLE
            }
            if (it.shop == 1) {//渠道是否创建 1绑定;-1没绑定
                mDatabind.tvIschannel.visibility = View.GONE
            } else {
                mDatabind.tvIschannel.visibility = View.VISIBLE
            }
        }
        mViewModel.disableAccountDto.onStart.observe(this) {
            showLoading("请求中")
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

            }
        }
        mViewModel.shopBean.onError.observe(this) {
            dismissLoading()
            showToast(it.msg)
        }

    }

}