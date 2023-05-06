package com.meiling.oms.dialog

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import android.view.Gravity
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
import com.meiling.common.network.data.*
import com.meiling.common.network.service.accountService
import com.meiling.oms.R
import com.meiling.oms.widget.showToast
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder


@Suppress("DEPRECATION")
class AccountSelectShopOrCityDialog : BaseNiceDialog() {
    init {
        setHeight(400)
        setGravity(Gravity.BOTTOM)
        setOutCancel(false)
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_account_select_shop_city
    }

    private lateinit var rvSelectAdapter: BaseQuickAdapter<PoiContentList, BaseViewHolder>

    fun newInstance(
        title: String
    ): AccountSelectShopOrCityDialog {
        val args = Bundle()
        args.putString("title", title)
        val dialog = AccountSelectShopOrCityDialog()
        dialog.arguments = args
        return dialog
    }

    var pageIndex = 1
    var arrayList = ArrayList<ShopPoiDto>()
    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        var rvSelect = holder?.getView<RecyclerView>(R.id.rv_shop_or_city)
        var btnSelect = holder?.getView<ShapeButton>(R.id.btn_ok_select_shop_city)
        var close = holder?.getView<ImageView>(R.id.iv_close_select_shop_city)
        var ivSelectAll = holder?.getView<ImageView>(R.id.img_select_all)
        var llSelectAll = holder?.getView<LinearLayout>(R.id.ll_select_all)
        var title = holder?.getView<TextView>(R.id.txt_title_item_city)
        title?.text = arguments?.getString("title")

        close?.setOnClickListener {
            dismiss()
        }
        var isSelectAll = false
        ivSelectAll?.setImageDrawable(resources.getDrawable(R.drawable.icon_checkbox_false))
        rvSelectAdapter = object :
            BaseQuickAdapter<PoiContentList, BaseViewHolder>(R.layout.item_dialog_account_select_city_or_shop),
            LoadMoreModule {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun convert(holder: BaseViewHolder, item: PoiContentList) {
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
        rvSelectAdapter.setOnItemClickListener { adapter, view, position ->
            (rvSelectAdapter.data[position] as PoiContentList).isSelect =
                !(rvSelectAdapter.data[position] as PoiContentList).isSelect
            rvSelectAdapter.notifyItemChanged(position)
            if ((rvSelectAdapter.data[position] as PoiContentList).isSelect) {
                arrayList.add(ShopPoiDto(poiIds = (rvSelectAdapter.data[position] as PoiContentList).id))
            } else {
                arrayList.remove(ShopPoiDto(poiIds = (rvSelectAdapter.data[position] as PoiContentList).id))
            }

            if (rvSelectAdapter.data.all { it.isSelect }) {
                isSelectAll = true
                ivSelectAll?.setImageDrawable(resources.getDrawable(R.drawable.icon_checkbox_true))
            } else {
                isSelectAll = false
                ivSelectAll?.setImageDrawable(resources.getDrawable(R.drawable.icon_checkbox_false))
            }

        }


        btnSelect?.setOnClickListener {
            okSelectItemClickLister?.invoke(
                arrayList,
                "0",
            )
            dismiss()
        }

        llSelectAll?.setOnClickListener {
            if (isSelectAll) {
                isSelectAll = false
                for (item in rvSelectAdapter.data) {
                    item.isSelect = false
                    ivSelectAll?.setImageDrawable(resources.getDrawable(R.drawable.icon_checkbox_false))
                    rvSelectAdapter.notifyDataSetChanged()
                }
            } else {
                isSelectAll = true
                for (item in rvSelectAdapter.data) {
                    item.isSelect = true
                    ivSelectAll?.setImageDrawable(resources.getDrawable(R.drawable.icon_checkbox_true))
                    rvSelectAdapter.notifyDataSetChanged()
                }
            }
        }
        initData()
    }


    fun initData() {
        var createSelectPoiDto = BaseLiveData<CreateSelectPoiDto>()
        BaseViewModel(Application()).request(
            { accountService.getPoiList(1, "10") },
            createSelectPoiDto
        )
//        rvSelectAdapter.loadMoreModule.loadMoreView = SS()
//        rvSelectAdapter.loadMoreModule.setOnLoadMoreListener {
//            pageIndex++
//            BaseViewModel(Application()).request(
//                { accountService.getPoiList(pageIndex, "100") },
//                createSelectPoiDto
//            )
//        }
        BaseViewModel(Application()).request(
            { accountService.getPoiList(1, "100") },
            createSelectPoiDto
        )
        createSelectPoiDto.onSuccess.observe(this) {
            rvSelectAdapter.setList(it.content as MutableList<PoiContentList>)
//            if (it.pageIndex == 1) {
//                if (it.content.isNullOrEmpty()) {
//                    rvSelectAdapter.setList(null)
//                } else {
//                    rvSelectAdapter.setList(it.content as MutableList<PoiContentList>)
//                    rvSelectAdapter.notifyDataSetChanged()
//                }
//            } else {
//                rvSelectAdapter.addData(it.content as MutableList<PoiContentList>)
//                rvSelectAdapter.notifyDataSetChanged()
//            }
//            if (it.content!!.size < 10) {
//                rvSelectAdapter.footerWithEmptyEnable = false
//                rvSelectAdapter.footerLayout?.visibility = View.GONE
//                rvSelectAdapter.loadMoreModule.loadMoreEnd()
//                rvSelectAdapter.notifyDataSetChanged()
//            } else {
//                rvSelectAdapter.loadMoreModule.loadMoreComplete()
//            }
        }
        createSelectPoiDto.onStart.observe(this) {
        }
        createSelectPoiDto.onError.observe(this) {
            showToast(it.msg)
        }
    }

    var okSelectItemClickLister: ((arrayList: ArrayList<ShopPoiDto>, isSelectAll: String) -> Unit)? =
        null

    fun setOkClickItemLister(okClickLister: (arrayList: ArrayList<ShopPoiDto>, isSelectAll: String) -> Unit) {
        this.okSelectItemClickLister = okClickLister
    }
}