package com.meiling.account.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.meihao.kotlin.cashier.db.ArticleDao
import com.meihao.kotlin.cashier.db.ArticleGoosDataBase
import com.meihao.kotlin.cashier.db.GoosClassifyDaoDao
import com.meihao.kotlin.cashier.db.GoosClassifyDataBase
import com.meiling.account.R
import com.meiling.account.adapter.FragAdapter
import com.meiling.account.databinding.ActivityMainBinding
import com.meiling.account.dialog.HelfenDialog
import com.meiling.account.dialog.MineExitDialog
import com.meiling.account.eventBusData.MessageEvent
import com.meiling.account.fragment.HomeFragment
import com.meiling.account.fragment.RecordsCenterFragment
import com.meiling.account.viewmodel.MainViewModel
import com.meiling.account.widget.UpdateVersion
import com.meiling.account.widget.setSingleClickListener
import com.meiling.account.widget.showToast
import com.meiling.common.GlideApp
import com.meiling.common.activity.BaseActivity
import com.meiling.common.constant.ARouteConstants
import com.meiling.common.utils.GlideCircleTransform
import com.meiling.common.utils.MMKVUtils
import com.meiling.common.utils.ScreenManager
import com.meiling.common.utils.VibrationUtils
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


//主页……
@Route(path = "/app/MainActivity")
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(),
    ViewPager.OnPageChangeListener {
    var fragments = ArrayList<Fragment>()

    var screenManager: ScreenManager = ScreenManager.getInstance()

    override fun isStatusBarEnabled(): Boolean {
        return false
    }
    val articleDao: ArticleDao =
        ArticleGoosDataBase.instance.getGoodsToOrderContentDao()
    val goodsCategoryDao: GoosClassifyDaoDao = GoosClassifyDataBase.instance.getGoodsCategoryDao()

    override fun initView(savedInstanceState: Bundle?) {
        screenManager.initPayLayoutWidth(this)
        mDatabind.menuHelfen.setSingleClickListener {
            HelfenDialog().show(supportFragmentManager)
          //  HelfenPopWindow.Builder(this).showAtLocation(mDatabind.viewpager)
        }
        mDatabind.menuSetting1.setOnClickListener {
            var vibrationUtils = VibrationUtils(this)
            vibrationUtils.vibrate()
           startActivity(Intent(this,SettingActivity::class.java))

        }


        mDatabind.menuShouyin1.setSingleClickListener {
            mDatabind.viewpager.setCurrentItem(0, false)
        }
        mDatabind.menuOrderSearch.setSingleClickListener {
            mDatabind.viewpager.setCurrentItem(1, false)
        }
        mDatabind.viewpager.addOnPageChangeListener(this);
        setincon(0)
        mDatabind.viewpager.setNoScroll(true);

        setmessage()
        mDatabind.menuShopReceive.setSingleClickListener {
            val dialog: MineExitDialog =
                MineExitDialog().newInstance("退出登录", "确定要退出登录当前账号吗？", "取消", "确认", false)
            dialog.setOkClickLister {
                ToastUtils.showShort("账号登录过期，请重新登录")
                MMKVUtils.clear()
                articleDao.deleteAll()
                goodsCategoryDao.deleteAll()

                ARouter.getInstance().build(ARouteConstants.LOGIN_ACTIVITY)
                    .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).navigation()
                ActivityUtils.finishAllActivities()
                // mViewModel.setUmengToken()

            }
            dialog.show(supportFragmentManager)
        }

        MMKVUtils.putBoolean("isUpdate", true)
        mDatabind.viewpager.setOffscreenPageLimit(0)

    }


    //设置用户信息
    fun setmessage() {
        Log.d("yjk","用户信息--"+userInfoBean?.userName)
        mDatabind.tvUserName.text=MyuserInfoBean()?.userName
        GlideApp.with(this)
            .load("https://lmg.jj20.com/up/allimg/4k/s/02/210924233115O14-0-lp.jpg")
            .circleCrop()

            .transform(GlideCircleTransform(this, 1, this.getResources().getColor(R.color.white)))

            .into(mDatabind.ivHeadPortraits)
    }


    override fun onResume() {
        super.onResume()
        if (MMKVUtils.getBoolean("isUpdate")) {
            UpdateVersion.getUpdateVersion(this, "0")
        }

    }

    override fun initData() {
        Log.d("mkl", "initData: ")

        mViewModel.userInfo()

        fragments.add(HomeFragment())

        fragments.add(RecordsCenterFragment())
        var fragAdapter = FragAdapter(supportFragmentManager, fragments)
        mDatabind.viewpager.adapter = fragAdapter

    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }


    override fun initListener() {

    }

    override fun createObserver() {
        mViewModel.userBean.onStart.observe(this) {
            showLoading("请稍后...")
        }
        mViewModel.userBean.onSuccess.observe(this) {
            disLoading()
            SaveUserBean(it)
            setmessage()
            //startActivity(Intent(this, SelectStoreActiviy::class.java))
        }
        mViewModel.userBean.onError.observe(this) {
            disLoading()
            showToast(it.msg)
        }
    }


    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        doubleBackToExitPressedOnce = true
        Toast.makeText(this, "再按一次返回键退出应用", Toast.LENGTH_SHORT).show()
        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }


    override fun onDestroy() {
        super.onDestroy()
       // EventBus.getDefault().unregister(this)
//        mainActivity=null
//            EventBus.getDefault().unregister(this)
        // NetworkMonitorManager.getInstance().unregister(this)


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        setincon(position)
    }

    //设置图标和文字
    fun setincon(int: Int) {
        if (int == 1) {
            // 完工入库选择
            mDatabind.menuShouyin1.setBackgroundResource(R.color.red)
            mDatabind.menuShouyinImg.setImageResource(R.drawable.home_tab_main_unselected)
            mDatabind.tvShouyin.setTextColor(resources.getColor(R.color.white))


            mDatabind.menuOrderSearch.setBackgroundResource(R.drawable.bg_whele_let_5)
            mDatabind.menuImgOrderSearch.setImageResource(R.drawable.home_data1)
            mDatabind.menuOrderText.setTextColor(resources.getColor(R.color.red))
        } else {
            // 完工入库没选择
            mDatabind.menuShouyin1.setBackgroundResource(R.drawable.bg_whele_let_5)
            mDatabind.tvShouyin.setTextColor(resources.getColor(R.color.red))
            mDatabind.menuShouyinImg.setImageResource(R.drawable.finished_warehousing_yet)

            mDatabind.menuImgOrderSearch.setImageResource(R.drawable.home_data)
            mDatabind.menuOrderSearch.setBackgroundResource(R.color.red)
            mDatabind.menuOrderText.setTextColor(resources.getColor(R.color.white))

        }
    }

    override fun onPageScrollStateChanged(state: Int) {
    }


    override fun isCheckNetWork(): Boolean {
        return true
    }



}