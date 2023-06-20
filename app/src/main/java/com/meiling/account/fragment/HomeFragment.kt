package com.meiling.account.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.meiling.account.R
import com.meiling.account.adapter.CustomkeyboardAdapter
import com.meiling.account.adapter.GoodaAdapter
import com.meiling.account.adapter.TabAdapter
import com.meiling.account.bean.Appdata
import com.meiling.account.bean.GoosClassify
import com.meiling.account.databinding.FragmentHomeBinding
import com.meiling.account.viewmodel.MainViewModel
import com.meiling.account.widget.InputUtil
import com.meiling.account.widget.showToast
import com.meiling.common.fragment.BaseFragment
import com.meiling.common.utils.RecyclerViewDivider

//完工入库
class HomeFragment : BaseFragment<MainViewModel, FragmentHomeBinding>() , OnItemClickListener {
    //键盘
    var customkeyboardAdapter: CustomkeyboardAdapter? = null

    var goodaAdapter: GoodaAdapter? = null

    //分类more
    private var tabAdapter: TabAdapter? = null


    var data = ArrayList<GoosClassify>()
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
        goodaAdapter?.setList(InputUtil.getyouhuiString())


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
        if (adapter==customkeyboardAdapter){
            if (!TextUtils.isEmpty(customkeyboardAdapter?.getItem(position))) {
                if (customkeyboardAdapter?.getItem(position).equals("20")) {
                    num = ""
                   mDatabind.stockAddNum .text = num
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
        }
    }


}