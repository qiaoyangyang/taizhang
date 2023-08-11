package com.meiling.account.fragment

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meihao.kotlin.cashier.db.ArticleDao
import com.meihao.kotlin.cashier.db.ArticleGoosDataBase
import com.meihao.kotlin.cashier.db.GoosClassifyDaoDao
import com.meihao.kotlin.cashier.db.GoosClassifyDataBase
import com.meiling.account.R
import com.meiling.account.bean.DateSplitList
import com.meiling.account.data.AndIn
import com.meiling.account.data.RefreshData
import com.meiling.account.databinding.FragmnetSwitchStoresBinding
import com.meiling.account.viewmodel.LoginViewModel
import com.meiling.account.widget.showToast
import com.meiling.common.fragment.BaseFragment
import com.meiling.common.network.data.UserStoreList
import com.meiling.common.utils.SpannableUtils
import org.greenrobot.eventbus.EventBus

//切换门店
class SwitchStoresFragment : BaseFragment<LoginViewModel, FragmnetSwitchStoresBinding>(),
    OnItemClickListener, TextWatcher {
    //左侧添加列表addListAdapter
    lateinit var selectShopAdapger: BaseQuickAdapter<UserStoreList, BaseViewHolder>
    val articleDao: ArticleDao =
        ArticleGoosDataBase.instance.getGoodsToOrderContentDao()
    val goodsCategoryDao: GoosClassifyDaoDao = GoosClassifyDataBase.instance.getGoodsCategoryDao()

    override fun initView(savedInstanceState: Bundle?) {

        dadapter()
        storeName()


        mDatabind.tvModifyStore.setOnClickListener {
            mDatabind.tvModifyStore.text = "请选择："

        }
        mDatabind.tvModifyStore.addTextChangedListener(this)
        mDatabind.btnOkExit.setOnClickListener {
            mDatabind.tvModifyStore.text = "修改门店"
            isisselect=false
            selectShopAdapger.notifyDataSetChanged()

        }
        mDatabind.btnCancelExit.setOnClickListener {
            if (userStore==null){
                showToast("请选择门店")
                return@setOnClickListener
            }
            articleDao.deleteAll()
            goodsCategoryDao.deleteAll()
            SaveUserStoreList(userStore)
            EventBus.getDefault().post(RefreshData("2"))
            storeName()
            mDatabind.tvModifyStore.text = "修改门店"
            isisselect=false
            selectShopAdapger.notifyDataSetChanged()

        }
    }

    override fun getBind(inflater: LayoutInflater): FragmnetSwitchStoresBinding {
        return FragmnetSwitchStoresBinding.inflate(inflater)
    }

    override fun initData() {
        super.initData()
        mViewModel.userStoreList()
    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.UserStoreList.onStart.observe(this) {
            showLoading("")
        }
        mViewModel.UserStoreList.onSuccess.observe(this) {
            dismissLoading()
            selectShopAdapger.setList(it)

        }
        mViewModel.UserStoreList.onError.observe(this) {
            dismissLoading()
            showToast(it.msg)

        }
    }
    var userStore:UserStoreList?=null
    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        if (mDatabind.tvModifyStore.text.toString() == "请选择：") {
            isisselect=true
            selectShopAdapger.data.forEach {
                it.isselect=false
            }
            selectShopAdapger.data.get(position).isselect=true
            userStore = selectShopAdapger.data.get(position)
            selectShopAdapger.notifyDataSetChanged()
        }

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(s: Editable?) {
        if (mDatabind.tvModifyStore.text.toString() == "请选择：") {
            mDatabind.llBtn.visibility = View.VISIBLE
        } else {
            mDatabind.llBtn.visibility = View.GONE
        }
    }
    var isisselect=false
    fun dadapter() {

        selectShopAdapger = object :
            BaseQuickAdapter<UserStoreList, BaseViewHolder>(R.layout.item_recy_select_shop1) {
            override fun convert(holder: BaseViewHolder, item: UserStoreList) {
                holder.setText(R.id.txtRecySelectShopItem, item.storeName)
                if (!isisselect) {
                    holder.setTextColor(R.id.txtRecySelectShopItem,Color.parseColor("#303133"))
                    if (userStoreList()?.viewId == item.viewId) {
                        holder.setBackgroundResource(R.id.cl_bg, R.drawable.bg_login_true)
                    } else {
                        holder.setBackgroundResource(R.id.cl_bg, R.drawable.bg_login_false)
                    }
                }else{
                    if (item.isselect) {
                        holder.setTextColor(R.id.txtRecySelectShopItem,Color.parseColor("#FFFFFF"))
                        holder.setBackgroundResource(R.id.cl_bg, R.drawable.bg_login_true2)
                    } else {
                        holder.setTextColor(R.id.txtRecySelectShopItem,Color.parseColor("#303133"))
                        holder.setBackgroundResource(R.id.cl_bg, R.drawable.bg_login_false)
                    }
                }
            }

        }
        mDatabind.rySelectShop.adapter = selectShopAdapger
        selectShopAdapger.setOnItemClickListener(this)

    }
    private fun storeName(){
        var s = "当前数据为“${userStoreList()?.storeName}”数据"
        SpannableUtils.setTextcolor(
            activity,
            s,
            mDatabind.tvUserName,
            6,
            s.length - 3,
            com.meiling.common.R.color.tv_red

        )
    }


}