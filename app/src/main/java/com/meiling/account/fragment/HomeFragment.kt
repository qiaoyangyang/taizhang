package com.meiling.account.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.github.promeg.pinyinhelper.Pinyin
import com.hjq.base.BasePopupWindow
import com.hjq.base.action.AnimAction
import com.meihao.kotlin.cashier.db.ArticleDao
import com.meihao.kotlin.cashier.db.ArticleGoosDataBase
import com.meihao.kotlin.cashier.db.GoosClassifyDaoDao
import com.meihao.kotlin.cashier.db.GoosClassifyDataBase
import com.meiling.account.R
import com.meiling.account.adapter.CustomkeyboardAdapter
import com.meiling.account.adapter.GoodaAdapter
import com.meiling.account.adapter.MainFlowLayoutAdapter
import com.meiling.account.adapter.TabAdapter
import com.meiling.account.bean.Goods
import com.meiling.account.bean.GoodsController
import com.meiling.account.bean.GoosClassify
import com.meiling.account.bean.StorageGoods
import com.meiling.account.databinding.FragmentHomeBinding
import com.meiling.account.dialog.ClassificationPopWindow
import com.meiling.account.viewmodel.MainViewModel
import com.meiling.account.widget.InputUtil
import com.meiling.account.widget.KeyBoardUtil
import com.meiling.account.widget.setSingleClickListener
import com.meiling.account.widget.showToast
import com.meiling.common.GlideApp
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


    val goodsCategoryDao: GoosClassifyDaoDao = GoosClassifyDataBase.instance.getGoodsCategoryDao()

    override fun initView(savedInstanceState: Bundle?) {
        setclassification()
        //键盘
        mDatabind.rvKeyboard?.layoutManager = GridLayoutManager(context, 3)
        customkeyboardAdapter = CustomkeyboardAdapter(R.layout.item_customkey)
        mDatabind.rvKeyboard?.adapter = customkeyboardAdapter
        customkeyboardAdapter!!.setList(InputUtil.getyouhuiString())
        customkeyboardAdapter?.setOnItemClickListener(this)


        mDatabind.commodityStockRcy?.layoutManager = GridLayoutManager(context, 3)
        goodaAdapter = GoodaAdapter(R.layout.item_goods)
        mDatabind.commodityStockRcy?.adapter = goodaAdapter

        goodaAdapter?.setOnItemClickListener(this)

        setisselect(isselect)
        mDatabind.stockSearchGoodEdit.setOnEditorActionListener { v, actionId, event ->
            if ((actionId == 0 || actionId == 3) && event != null) {
                //点击搜索
                sortCode = mDatabind.stockSearchGoodEdit.text.toString()

                var goods = articleDao.getGoodsByKeyWord(sortCode) as ArrayList<Goods>
                goodaAdapter?.setList(goods)
                KeyBoardUtil.closeKeyBord(mDatabind.stockSearchGoodEdit, requireContext())
            }
            return@setOnEditorActionListener false
        }
        mDatabind.goodsStockSearchImgLayout.setOnClickListener {
            sortCode = mDatabind.stockSearchGoodEdit.text.toString()

            var goods = articleDao.getGoodsByKeyWord(sortCode) as ArrayList<Goods>
            goodaAdapter?.setList(goods)
            KeyBoardUtil.closeKeyBord(mDatabind.stockSearchGoodEdit, requireContext())
        }

        //监听输入是否有内容  有内容显示删除按钮
        mDatabind.stockSearchGoodEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (mDatabind.stockSearchGoodEdit.text.toString().isEmpty()) {
                    mDatabind.goodsStockSearchGoodClear.visibility = View.GONE
                } else {
                    mDatabind.goodsStockSearchGoodClear.visibility = View.VISIBLE
                }
            }

        })
        //删除搜素内容
        mDatabind.goodsStockSearchGoodClear.setSingleClickListener {
            sortCode = ""
            mDatabind.stockSearchGoodEdit.setText(sortCode)

            var goods = articleDao.getGoodsByKeyWord(sortCode) as ArrayList<Goods>
            goodaAdapter?.setList(goods)

        }
        //良品入库
        mDatabind.tvGoodProductsAreStored.setSingleClickListener {

            Log.d("yjk", "良品入库: " + userStoreList()?.tenantId)
            //
            if (TextUtils.isEmpty(mDatabind.stockAddNum.text.toString())) {
                showToast("请输入数量")
                return@setSingleClickListener
            }
            goodsType = 1
            setstorageGood()
        }
        //不良品入库
        mDatabind.tvDefectiveProductsAreStored.setSingleClickListener {
            // setissucceed(false)
            if (TextUtils.isEmpty(mDatabind.stockAddNum.text.toString())) {
                showToast("请输入数量")
                return@setSingleClickListener
            }
            goodsType = 2
            setstorageGood()

        }
        //加号
        mDatabind.stockAddJia.setSingleClickListener {
            num = mDatabind.stockAddNum.text.toString()
            mDatabind.stockAddNum.text = XNumberUtils.enquiryAdd(num, "1")
        }
        //减
        mDatabind.stockAddJian.setSingleClickListener {
            num = mDatabind.stockAddNum.text.toString()
            if (XNumberUtils.compareTo(num, "1") != 1) {
                return@setSingleClickListener
            }

            mDatabind.stockAddNum.text = XNumberUtils.enquirysubtract(num, "1")
        }
        //分类的对话框
        mDatabind.inventoryStockTabMore.setSingleClickListener {
            ClassificationPopWindow.Builder(mActivity).setGravity(Gravity.RIGHT).setAnimStyle(
                AnimAction.ANIM_EMPTY
            ).setListener(object : ClassificationPopWindow.OnListener {
                override fun onSelected(
                    popupWindow: BasePopupWindow?, id: Int, boolean: Boolean
                ) {
                    if (boolean == false) {
                        tabAdapter?.data?.forEach {
                            it.select = false

                        }

                        var item = tabAdapter?.getItem(id)
                        tabAdapter?.getItem(id)?.select = true
                        tabAdapter?.notifyDataSetChanged()

                        if (TextUtils.isEmpty(item?.sortCode)) {
                            var goods = articleDao.getGoodsByKeyWord(sortCode) as ArrayList<Goods>
                            goodaAdapter?.setList(goods)
                        } else {
                            var goods =
                                articleDao.getGoodsByCategoryId(item?.sortCode!!) as ArrayList<Goods>
                            goodaAdapter?.setList(goods)
                        }
                    } else {
                        pageNum = 1
                        goodsCategoryDao.deleteAll()
                        articleDao.deleteAll()
                        initData()
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
            var item = tabAdapter?.getItem(position)

            tabAdapter?.notifyDataSetChanged()
            if (TextUtils.isEmpty(item?.sortCode)) {
                var goods = articleDao.getGoodsByKeyWord(sortCode) as ArrayList<Goods>
                goodaAdapter?.setList(goods)
            } else {
                var goods = articleDao.getGoodsByKeyWord(item?.sortCode!!) as ArrayList<Goods>
                goodaAdapter?.setList(goods)
            }


        }


    }

    val articleDao: ArticleDao =
        ArticleGoosDataBase.instance.getGoodsToOrderContentDao()
    var pageNum: Int = 1
    var pageSize: Int = 10
    var sortCode: String = ""
    override fun initData() {
        super.initData()

        if (goodsCategoryDao.selectGoodsCount() == 0) {
            mViewModel.sorlistt()
        } else {
            data = goodsCategoryDao.getCategoryname() as ArrayList<GoosClassify>
            data[0].select = true
            tabAdapter?.setList(data)
        }

        if (articleDao.selectGoodsCount() == 0) {
            mViewModel.goodslistt(GoodsController(pageNum, pageSize, sortCode))
        } else {

            var goods = articleDao.getGoodsByKeyWord(sortCode) as ArrayList<Goods>
            goodaAdapter?.setList(goods)
        }
    }

    var num: String = "1"
    var isaadd: Boolean = false
    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        if (adapter == customkeyboardAdapter) {
            if (!TextUtils.isEmpty(customkeyboardAdapter?.getItem(position))) {
                if (customkeyboardAdapter?.getItem(position).equals("20")) {
                    num = ""
                    mDatabind.stockAddNum.text = num
                } else {

                    if (!isaadd) {
                        isaadd = true
                        num = ""
                        mDatabind.stockAddNum.text = num
                    }

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


                    //num =mDatabind.stockAddNum.text.toString()+ customkeyboardAdapter?.getItem(position)
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
            setgoods(goodaAdapter?.getItem(position)!!)


        }
    }

    var goods: Goods? = null

    private fun setgoods(goodsdata: Goods) {

        if (goods != null) {
            if (goods?.viewId == goodsdata.viewId) {
                num = XNumberUtils.enquiryAdd(mDatabind.stockAddNum.text.toString(), "1")
            } else {
                isaadd = false
                num = "1"
            }

        }
        this.goods = goodsdata


        mDatabind.tvSku.text = goods?.skuCode
        mDatabind.stockAddNum.text = num
       // mDatabind.tvGoodsSpecsValus.text = goods?.goodsSpecsValus
        var mainFlowLayoutAdapter =
            MainFlowLayoutAdapter(InputUtil.getSpezifikation(goods?.goodsSpecsValus))
        mDatabind.flashLightLayout.setAdapter(mainFlowLayoutAdapter)
        mainFlowLayoutAdapter.setiscompile(true)



        mDatabind.tvGoodsUnit.text = "入库数量 （单位：${goods?.goodsUnit}）："
        mDatabind.tvName.text = goods?.goodsName
        GlideApp.with(this)
            .load(goods?.goodsImgurl)
            .into(mDatabind.ivCommodityUrl)

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
    private fun setissucceed(b: Int) {
        Log.d("mkl", "是否成功: ")
        if (b == 1) {
            mDatabind.tvIsSucceed.text = "良品入库成功"
            TextDrawableUtils.setTopDrawable(mDatabind.tvIsSucceed, R.drawable.succeed)
        } else if (b == 2) {
            mDatabind.tvIsSucceed.text = "不良品入库成功"
            TextDrawableUtils.setTopDrawable(mDatabind.tvIsSucceed, R.drawable.be_defeated)
        }
        if (goods != null) {
            mDatabind.clSelect.visibility = View.GONE
            mDatabind.llSelect.visibility = View.GONE
            mDatabind.clSucceed.visibility = View.VISIBLE
            mDatabind.produceGoodsName.text = goods?.goodsName
            mDatabind.tvSelectSum.text = mDatabind.stockAddNum.text.toString() + goods?.goodsUnit
        }else{
            mDatabind.clSelect.visibility = View.GONE
            mDatabind.llSelect.visibility = View.VISIBLE
            mDatabind.clSucceed.visibility = View.GONE
        }
        num = "1"
        isaadd = false
        goods = Goods()

    }

    override fun createObserver() {
        super.createObserver()

        mViewModel.goosClassify.onStart.observe(this) {
            showLoading("")
        }

        mViewModel.goosClassify.onSuccess.observe(this) {
            dismissLoading()
            var category = GoosClassify(id = "0", sortName = "全部商品")
            category.select = true
            it.add(0, category)
            data = it

            goodsCategoryDao.insertAll(it)
            tabAdapter?.setList(data)


        }

        mViewModel.goosClassify.onError.observe(this) {
            dismissLoading()
            showToast(it.msg)
        }


        mViewModel.goods.onStart.observe(this) {
            showLoading("")
        }

        mViewModel.goods.onSuccess.observe(this) {

            var div = (it.total - 1) / pageSize + 1

            var isLoadAll = pageNum == div
            it.data?.forEach {
                var allChinesePinyin = Pinyin.toPinyin(it?.goodsName, "|")
                //获取完整拼音
                var allChinesePinyinList = allChinesePinyin.split("|")
                var allChinesePinyinTemp = StringBuffer()
                allChinesePinyinList.forEach {
                    allChinesePinyinTemp.append(it)
                }
                it?.chineseAllPinYin = allChinesePinyinTemp.toString()
                //获取首字母
                var s = StringBuffer()
                var pinyinList = allChinesePinyin?.split("|")
                pinyinList?.forEach {
                    if (it.first() in 'a'..'z' || it.first() in 'A'..'Z') {
                        s.append(it.first())
                    } else {
                        s.append(it)
                    }
                }
                it?.chineseFirstPinYin = s.toString()
            }
            articleDao.insertAll(it?.data as ArrayList<Goods>)
            if (isLoadAll) {
                dismissLoading()


                var goods = articleDao.getGoodsByKeyWord(sortCode) as ArrayList<Goods>
                goodaAdapter?.setList(goods)

            } else {
                pageNum += 1
                mViewModel.goodslistt(GoodsController(pageNum, pageSize, sortCode))
            }


        }

        mViewModel.goods.onError.observe(this) {
            dismissLoading()
            showToast(it.msg)
        }

        mViewModel.storageGoodssave.onStart.observe(this) {
            showLoading("")


        }
        mViewModel.storageGoodssave.onSuccess.observe(this) {
            dismissLoading()
                setissucceed(goodsType)
        }
        mViewModel.storageGoodssave.onError.observe(this) {
            dismissLoading()
            showToast(it.msg)
        }

    }

    var goodsType = -1;

    // 商品入库
    fun setstorageGood() {
        if (goodsType != -1) {
            mViewModel.storageGood(
                StorageGoods(
                    goodsType,
                    goods?.viewId!!,
                    mDatabind.stockAddNum.text.toString(),
                    userStoreList()?.storeName!!,
                    userStoreList()?.viewId!!
                )
            )
        }

    }


}