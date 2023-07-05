package com.meiling.account.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.hjq.base.BasePopupWindow
import com.hjq.base.action.AnimAction
import com.meiling.account.R
import com.meiling.account.adapter.CustomkeyboardAdapter
import com.meiling.account.adapter.GoodaAdapter
import com.meiling.account.adapter.TabAdapter
import com.meiling.account.bean.Appdata
import com.meiling.account.bean.GoosClassify
import com.meiling.account.databinding.FragmentHomeBinding
import com.meiling.account.dialog.ClassificationPopWindow
import com.meiling.account.viewmodel.MainViewModel
import com.meiling.account.widget.InputUtil
import com.meiling.account.widget.KeyBoardUtil
import com.meiling.account.widget.setSingleClickListener
import com.meiling.account.widget.showToast
import com.meiling.common.fragment.BaseFragment
import com.meiling.common.utils.RecyclerViewDivider
import com.meiling.common.utils.TextDrawableUtils
import com.meiling.common.utils.XNumberUtils

//完工入库
class HomeFragment : BaseFragment<MainViewModel, FragmentHomeBinding>(), OnItemClickListener {
    //键盘
    var customkeyboardAdapter: CustomkeyboardAdapter? = null

    //商品
    var goodaAdapter: GoodaAdapter? = null

    //分类more
    private var tabAdapter: TabAdapter? = null


    var data = ArrayList<GoosClassify>()
    var isselect = false
    var searchkey=""//  搜素关键字

    override fun initView(savedInstanceState: Bundle?) {
        data = Appdata.getGoosClassify()
        setclassification()

        mDatabind.rvKeyboard?.layoutManager = GridLayoutManager(context, 3)
        customkeyboardAdapter = CustomkeyboardAdapter(R.layout.item_customkey)
        mDatabind.rvKeyboard?.adapter = customkeyboardAdapter
        customkeyboardAdapter!!.setList(InputUtil.getyouhuiString())
        customkeyboardAdapter?.setOnItemClickListener(this)


        mDatabind.commodityStockRcy?.layoutManager = GridLayoutManager(context, 3)
        goodaAdapter = GoodaAdapter(R.layout.item_goods)
        mDatabind.commodityStockRcy?.adapter = goodaAdapter

        goodaAdapter?.setList(InputUtil.getisExpen())
        goodaAdapter?.setOnItemClickListener(this)

        setisselect(isselect)
        mDatabind.stockSearchGoodEdit.setOnEditorActionListener { v, actionId, event ->
            if ((actionId == 0 || actionId == 3) && event != null) {
                //点击搜索
                // TODO:  搜索
                KeyBoardUtil.closeKeyBord( mDatabind.stockSearchGoodEdit, requireContext())
            }
            return@setOnEditorActionListener false
        }

        //监听输入是否有内容  有内容显示删除按钮
        mDatabind.stockSearchGoodEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (mDatabind.stockSearchGoodEdit.text.toString().isEmpty()) {
                    mDatabind.goodsStockSearchGoodClear.visibility=View.GONE
                }else{
                    mDatabind.goodsStockSearchGoodClear.visibility=View.VISIBLE
                }
            }

        })
        //删除搜素内容
        mDatabind.goodsStockSearchGoodClear.setSingleClickListener {
            searchkey=""
            mDatabind.stockSearchGoodEdit.setText(searchkey)

        }
        //良品入库
        mDatabind.tvGoodProductsAreStored.setSingleClickListener {
            setissucceed(true)
        }
        //不良品入库
        mDatabind.tvDefectiveProductsAreStored.setSingleClickListener {
            setissucceed(false)
        }
        //加号
        mDatabind.stockAddJia.setSingleClickListener {
            var num = mDatabind.stockAddNum.text.toString()
            mDatabind.stockAddNum.text = XNumberUtils.enquiryAdd(num, "1")
        }
        //减
        mDatabind.stockAddJian.setSingleClickListener {
            var num = mDatabind.stockAddNum.text.toString()
            if (XNumberUtils.compareTo(num, "1") != 1) {
                return@setSingleClickListener
            }

            mDatabind.stockAddNum.text = XNumberUtils.enquirysubtract(num, "1")
        }
        //分类的对话框
        mDatabind.inventoryStockTabMore.setSingleClickListener {
            ClassificationPopWindow.Builder(mActivity).setGravity(Gravity.RIGHT).setAnimStyle(
                AnimAction.ANIM_EMPTY
            )
                .setListener(object :
                    ClassificationPopWindow.OnListener {
                    override fun onSelected(
                        popupWindow: BasePopupWindow?,
                        id: Int,
                        boolean: Boolean
                    ) {
                        if (boolean == false) {
                            tabAdapter?.data?.forEach {
                                it.select = false

                            }
                            tabAdapter?.getItem(id)?.select = true
                            tabAdapter?.notifyDataSetChanged()
                        }
                    }

                }).setList(data).showAsDropDown(mDatabind.inventoryStockTabMore)
        }


    }

    override fun getBind(inflater: LayoutInflater): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater)
    }

    /**
     * 分类
     */
    fun setclassification() {
        tabAdapter = TabAdapter(R.layout.item_tab)
        mDatabind.inventoryRaw?.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        var recyclerViewDivider = RecyclerViewDivider(false)
        recyclerViewDivider.setDividerHeight(1)
        recyclerViewDivider.setMargin(0, 20, 0, 20)
        context?.let { recyclerViewDivider.setColor(it, R.color.c_66A1A3A6) }
        mDatabind.inventoryRaw?.addItemDecoration(recyclerViewDivider)

        mDatabind.inventoryRaw?.adapter = tabAdapter
        tabAdapter?.setOnItemClickListener { adapter, view, position ->
            tabAdapter?.data?.forEach {
                it.select = false

            }
            tabAdapter?.getItem(position)?.select = true
            tabAdapter?.notifyDataSetChanged()


        }
        tabAdapter?.setList(data)

        mDatabind.inventoryStockTabMore.setOnClickListener {

        }


    }

    var num: String = ""
    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        if (adapter == customkeyboardAdapter) {
            if (!TextUtils.isEmpty(customkeyboardAdapter?.getItem(position))) {
                if (customkeyboardAdapter?.getItem(position).equals("20")) {
                    num = ""
                    mDatabind.stockAddNum.text = num
                } else {

                    var start = num.indexOf(".")


//
                    if (customkeyboardAdapter?.getItem(position) == "." && TextUtils.isEmpty(num)) {
                        return
                    }
                    if (customkeyboardAdapter?.getItem(position) == "." && num.contains(".")) {
                        return
                    }
                    if (!TextUtils.isEmpty(num) && num.contains(".")) {
                        var endnane = num.substring(start + 1, num.length)
                        if (endnane.length >= 3) {

                            return
                        }
                    }



                    num += customkeyboardAdapter?.getItem(position)

                    mDatabind.stockAddNum.text = num
                }


            }
        } else if (adapter == goodaAdapter) {
            setisselect(true)
            goodaAdapter?.data?.forEach {
                it.isExpen = false

            }
            goodaAdapter?.data?.get(position)?.isExpen = true
            goodaAdapter?.notifyDataSetChanged()


        }
    }

    //是否选择
    private fun setisselect(b: Boolean) {
        mDatabind.clSucceed.visibility = View.GONE
        if (b) {
            mDatabind.clSelect.visibility = View.VISIBLE
            mDatabind.llSelect.visibility = View.GONE

        } else {
            mDatabind.llSelect.visibility = View.VISIBLE
            mDatabind.clSelect.visibility = View.GONE
        }
    }

    //是否成功
    private fun setissucceed(b: Boolean) {
        mDatabind.clSelect.visibility = View.GONE
        mDatabind.llSelect.visibility = View.GONE
        mDatabind.clSucceed.visibility = View.VISIBLE
        if (b) {
            TextDrawableUtils.setTopDrawable(mDatabind.tvIsSucceed, R.drawable.succeed)
        } else {
            TextDrawableUtils.setTopDrawable(mDatabind.tvIsSucceed, R.drawable.be_defeated)
        }

    }


}