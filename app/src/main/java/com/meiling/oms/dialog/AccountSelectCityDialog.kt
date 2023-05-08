package com.meiling.oms.dialog

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hjq.shape.view.ShapeButton
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.base.WheelItemView
import com.meiling.common.network.data.*
import com.meiling.common.network.service.accountService
import com.meiling.common.network.service.orderDisService
import com.meiling.oms.R
import com.meiling.oms.widget.SS
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder


@Suppress("DEPRECATION")
class AccountSelectCityDialog : BaseNiceDialog() {
    init {
        setHeight(400)
        setGravity(Gravity.BOTTOM)
        setOutCancel(false)
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_account_select_shop_city
    }

    private lateinit var rvSelectAdapter: BaseQuickAdapter<CreateShopBean, BaseViewHolder>

    fun newInstance(
        title: String,
        type: String,
        cityPoiDtoList: ArrayList<CityPoiDto>,
    ): AccountSelectCityDialog {
        val args = Bundle()
        args.putString("title", title)
        args.putString("type", type)
        args.putSerializable("cityPoiDtoList", cityPoiDtoList)
        val dialog = AccountSelectCityDialog()
        dialog.arguments = args
        return dialog
    }

    var pageIndex = 1
    var cityPoiDtoList = ArrayList<CityPoiDto>()
    var cityArrayList = ArrayList<CityPoiDto>()
    var type = "1"
    @SuppressLint("NotifyDataSetChanged")
    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        if (!arguments?.getString("type").isNullOrBlank()) {
            cityArrayList = arguments?.getSerializable("cityPoiDtoList") as ArrayList<CityPoiDto>
            type = arguments?.getString("type").toString()
        }

        var rvSelect = holder?.getView<RecyclerView>(R.id.rv_shop_or_city)
        var btnSelect = holder?.getView<ShapeButton>(R.id.btn_ok_select_shop_city)
        var close = holder?.getView<ImageView>(R.id.iv_close_select_shop_city)
        var ivSelectAll = holder?.getView<ImageView>(R.id.img_select_all)
        var llSelectAll = holder?.getView<LinearLayout>(R.id.ll_select_all)
        var title = holder?.getView<TextView>(R.id.txt_title_item_city)
        title?.text = arguments?.getString("title")
        var selectNum = holder?.getView<TextView>(R.id.txt_select_shop_or_city)

        close?.setOnClickListener {
            dismiss()
        }
        var isSelectAll = false
        ivSelectAll?.setImageDrawable(resources.getDrawable(R.drawable.icon_checkbox_false))
        rvSelectAdapter = object :
            BaseQuickAdapter<CreateShopBean, BaseViewHolder>(R.layout.item_dialog_account_select_city_or_shop),
            LoadMoreModule {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun convert(holder: BaseViewHolder, item: CreateShopBean) {
                var imgShopOrCity = holder.getView<ImageView>(R.id.img_shop_or_city)
                holder.setText(R.id.txt_shop_or_city, item.name)
                if (item.isSelect) {
                    imgShopOrCity.setImageDrawable(resources.getDrawable(R.drawable.icon_checkbox_true))
                } else {
                    imgShopOrCity.setImageDrawable(resources.getDrawable(R.drawable.icon_checkbox_false))
                }
            }

        }

        rvSelect?.adapter = rvSelectAdapter
        selectNum?.text = "已选择${rvSelectAdapter.data.count { it.isSelect }}个城市"
        rvSelectAdapter.setOnItemClickListener { adapter, view, position ->
            (rvSelectAdapter.data[position] as CreateShopBean).isSelect =
                !(rvSelectAdapter.data[position] as CreateShopBean).isSelect
            rvSelectAdapter.notifyItemChanged(position)

            if (rvSelectAdapter.data.all { it.isSelect }) {
                isSelectAll = true
                selectNum?.text = "已选择全部门店"
                ivSelectAll?.setImageDrawable(resources.getDrawable(R.drawable.icon_checkbox_true))
            } else {
                isSelectAll = false
                selectNum?.text = "已选择${rvSelectAdapter.data.count { it.isSelect }}个门店"
                ivSelectAll?.setImageDrawable(resources.getDrawable(R.drawable.icon_checkbox_false))
            }

        }

        llSelectAll?.setOnClickListener {
            if (isSelectAll) {
                isSelectAll = false
                for (item in rvSelectAdapter.data) {
                    item.isSelect = false
                    ivSelectAll?.setImageDrawable(resources.getDrawable(R.drawable.icon_checkbox_false))
                    rvSelectAdapter.notifyDataSetChanged()
                }
                selectNum?.text = "已选择${rvSelectAdapter.data.count { it.isSelect }}个城市"
            } else {
                isSelectAll = true
                selectNum?.text = "全部城市"
                for (item in rvSelectAdapter.data) {
                    item.isSelect = true
                    ivSelectAll?.setImageDrawable(resources.getDrawable(R.drawable.icon_checkbox_true))
                    rvSelectAdapter.notifyDataSetChanged()
                }
            }
        }



        btnSelect?.setOnClickListener {

            for (item in rvSelectAdapter.data) {
                if (item.isSelect) {
                    cityPoiDtoList.add(
                        CityPoiDto(
                            cityIds = item.id,
                            poiIds = item.shopList?.get(0)!!.id.toString()
                        )
                    )
                }
            }
            if (cityPoiDtoList.isNullOrEmpty()) {
                showToast("请选择城市")
                return@setOnClickListener
            }
            okSelectItemCityClickLister?.invoke(cityPoiDtoList, selectNum?.text.toString())
            dismiss()
        }
        initData()
    }

    fun initData() {
        var createSelectPoiDto = BaseLiveData<ArrayList<CreateShopBean>>()
        BaseViewModel(Application()).request(
            { accountService.getCityPoiList() },
            createSelectPoiDto
        )
        createSelectPoiDto.onSuccess.observe(this) {

            if (!type.isNullOrBlank()) {
//                对比显示
                var list = ArrayList<CreateShopBean>()
                list.addAll(it  as MutableList<CreateShopBean>)
                val poiIds = cityArrayList.map { shopPoiDtoList -> shopPoiDtoList.cityIds }.toSet()
                list.filter { poiContentList -> poiIds.contains(poiContentList.id) }
                    .forEach { poiContentList ->
                        // 找到了相同的 id
                        // 进行对应的处理
                        poiContentList.isSelect = true
                    }
                rvSelectAdapter.setList(list)
            } else {
                rvSelectAdapter.setList(it as MutableList<CreateShopBean>)
            }

        }
        createSelectPoiDto.onStart.observe(this) {
        }
        createSelectPoiDto.onError.observe(this) {
            showToast(it.msg)
        }
    }

    private var okSelectItemCityClickLister: ((cityDialog: ArrayList<CityPoiDto>, selectNum: String) -> Unit)? =
        null

    fun setOkClickItemCityLister(okClickLister: (cityDialog: ArrayList<CityPoiDto>, selectNum: String) -> Unit) {
        this.okSelectItemCityClickLister = okClickLister
    }
}