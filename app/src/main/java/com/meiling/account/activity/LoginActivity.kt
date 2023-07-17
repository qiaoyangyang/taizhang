package com.meiling.account.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hjq.base.action.AnimAction
import com.meihao.kotlin.cashier.db.UserLoginDao
import com.meihao.kotlin.cashier.db.UserLoginDataBase
import com.meiling.account.R
import com.meiling.account.bean.UserBean
import com.meiling.account.databinding.ActivityLoginBinding
import com.meiling.account.dialog.MineExitDialog
import com.meiling.account.dialog.PopLoginPopwindow
import com.meiling.account.jpush.PushHelper
import com.meiling.account.viewmodel.LoginViewModel
import com.meiling.account.widget.KeyBoardUtil
import com.meiling.account.widget.setSingleClickListener
import com.meiling.account.widget.showToast
import com.meiling.common.activity.BaseActivity
import com.meiling.common.constant.SPConstants
import com.meiling.common.network.data.userInfoBean
import com.meiling.common.utils.InputTextManager
import com.meiling.common.utils.MMKVUtils

//登陆页面
@Route(path = "/app/LoginActivity")
class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>() {

    var userLoginDao: UserLoginDao = UserLoginDataBase.instance.getUserLoginDao()

    override fun initView(savedInstanceState: Bundle?) {

        mDatabind.edtName.setText("15535958281")
        mDatabind.edtPaswd.setText("123456")
        //监听是否添加用户名和密码
        mDatabind.btnLogin.let {
            InputTextManager.with(this)
                .addView(mDatabind.edtName)
                .addView(mDatabind.edtPaswd)
                .setMain(it)
                .build()
            KeyBoardUtil.hideKeyBoard(this, mDatabind.btnLogin)
        }

        //登陆按钮
        mDatabind.btnLogin.setSingleClickListener {
            mViewModel.mobileLogin(
                mDatabind.edtName.text.toString(),
                mDatabind.edtPaswd.text.toString()
            )

            //setSaveaccount()
        }
        //清楚账号
        mDatabind.imgClear.setSingleClickListener {
            mDatabind.edtName.setText("")
        }
        //
        mDatabind.edtName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (TextUtils.isEmpty(mDatabind.edtName.text.toString())) {
                    mDatabind.imgClear.visibility = View.GONE
                } else {
                    mDatabind.imgClear.visibility = View.VISIBLE
                }
            }

        })
        mDatabind.goodsSearchMore.setOnClickListener {
            initPopShopList(mDatabind.llLoginName, userLoginDao.getStudentById(1))
        }


    }


    override fun getBind(layoutInflater: LayoutInflater): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.loginData.onStart.observe(this) {
            showLoading("请稍后...")
        }
        mViewModel.loginData.onSuccess.observe(this) {
            disLoading()
            MMKVUtils.putString(SPConstants.TOKEN, it.token!!)
            MMKVUtils.putString(SPConstants.tenantId, it.tenantId!!)
            MMKVUtils.putString(SPConstants.userViewId, it.userViewId!!)
            MMKVUtils.putString(SPConstants.userViewId, it.userViewId!!)
            showLoading("请稍后...")
            mViewModel.userInfo()

            // TODO:
            // startActivity(Intent(this, SelectStoreActiviy::class.java))
        }
        mViewModel.loginData.onError.observe(this) {
            disLoading()
            showToast(it.msg)
            //disLoading()
        }

        mViewModel.userBean.onStart.observe(this) {
            showLoading("请稍后...")
        }
        mViewModel.userBean.onSuccess.observe(this) {
            disLoading()
            SaveUserBean(it)
            setSaveaccount(it)
            MMKVUtils.putBoolean(SPConstants.LOGINSTASTS, true)
          //  startActivity(Intent(this, SelectStoreActiviy::class.java))
        }
        mViewModel.userBean.onError.observe(this) {
            disLoading()
            showToast(it.msg)
        }
    }

    override fun onResume() {
        super.onResume()
        hasNetWork(true)

    }


    fun setSaveaccount(userInfoBean: userInfoBean) {
        var listUser = userLoginDao.getAllUsers()

        val dialog: MineExitDialog =
            MineExitDialog().newInstance("温馨提示", "是否记住密码，以便下次直接登录？", "取消", "确认", false)
        dialog.setOkClickLister {

            // mViewModel.setUmengToken()
           //
            login(userInfoBean)

        }
        if (listUser.size == 0) {

            dialog.show(supportFragmentManager)
        } else {
            var userLoginInfo = userLoginDao.selectUserByUserId(userInfoBean.viewId.toString())
            if (userLoginInfo == null) {
                dialog.show(supportFragmentManager)
            } else {
                if (userLoginInfo.issave == 0) {
                    dialog.show(supportFragmentManager)
                } else {
                    login(userInfoBean)
                }
            }
        }
    }
    fun login(loginBean: userInfoBean) {

        //lwq
        Thread {
//            userLoginDao.deleteAll()
            var userBean = userLoginDao.selectUserByUserId(loginBean.viewId.toString())
            if (userBean != null) {
                userBean.issave = 1
                userBean.isselect = false

                userBean.password = mDatabind.edtPaswd.text.toString()
                userLoginDao.update(userBean)
            } else {
                userLoginDao.insert(
                    UserBean(
                        adminUserId = loginBean.viewId,
                        phone = loginBean.phone,
                        password = mDatabind.edtPaswd.text.toString(),


                        issave = 1,
                        isselect = false
                    )
                )
            }

            PushHelper.init(applicationContext)
        }.start()
        startActivity(Intent(this, SelectStoreActiviy::class.java))



    }
    lateinit var popVip: PopupWindow
    //选择密码的对话框
    fun initPopShopList(view: View, list: MutableList<UserBean>) {


        var popView = layoutInflater.inflate(R.layout.pop_login, null)
        popVip = PopupWindow(
            popView,
            view.width,
            200
            // ViewGroup.LayoutParams.MATCH_PARENT,
            // ViewGroup.LayoutParams.WRAP_CONTENT
        )
        popVip.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popVip.isOutsideTouchable = true
        popVip.animationStyle = AnimAction.ANIM_SCALE
        popVip.isFocusable = true

        if (list.size != 0) {
            popVip.showAsDropDown(view)
        }

        var cardTypeRecy = popView.findViewById<RecyclerView>(R.id.cardTypeRecy) as RecyclerView
        cardTypeRecy.layoutManager = LinearLayoutManager(this)
        var cardTypeRecyAdapter = object :
            BaseQuickAdapter<UserBean, BaseViewHolder>(R.layout.item_loggin) {
            override fun convert(holder: BaseViewHolder, item: UserBean) {

                holder.setText(R.id.item, item.phone)
                if (item.isselect!!) {
                    holder.setGone(R.id.tv_xian, false)
                    holder.setTextColor(R.id.item, Color.parseColor("#1F1F1F"))
                } else {
                    holder.setGone(R.id.tv_xian, true)
                    holder.setTextColor(R.id.item, Color.parseColor("#7A7A7A"))
                }

            }
        }

        cardTypeRecy.adapter = cardTypeRecyAdapter
        cardTypeRecyAdapter.setList(list)
        //  cardTypeRecyAdapter.setEmptyView(R.layout.empty_vip_recycler)
        cardTypeRecyAdapter.setOnItemClickListener { adapter, view2, position ->
            list.forEach {
                it.isselect = false
                userLoginDao.update(it)

            }
            val userBean = list.get(position)
            userBean.isselect = true
            userLoginDao.update(userBean)

            mDatabind.edtName.setText(cardTypeRecyAdapter.data.get(position).phone)

            mDatabind.edtPaswd.setText(cardTypeRecyAdapter.data.get(position).password)
            popVip.dismiss()

            //selectShopId = list.get(position).sid
        }

    }




}