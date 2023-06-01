package com.meiling.oms.dialog

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.network.data.*
import com.meiling.common.network.service.accountService
import com.meiling.oms.R
import com.meiling.oms.widget.showToast
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder


@Suppress("DEPRECATION")
class OrderSelectStoreDialog : BaseNiceDialog() {
    init {
        setHeight(400)
        setGravity(Gravity.BOTTOM)
        setOutCancel(false)
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_select_store
    }

    private lateinit var rvSelectAdapter: BaseQuickAdapter<PoiContentList, BaseViewHolder>

    fun newInstance(
        type: String,
    ): OrderSelectStoreDialog {
        val args = Bundle()
        args.putString("type", type)
        val dialog = OrderSelectStoreDialog()
        dialog.arguments = args
        return dialog
    }

    var pageIndex = 1
    var type = ""
    var isSelect = "0"
    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        type = arguments?.getString("type").toString()
        val rvSelect = holder?.getView<RecyclerView>(R.id.rv_shop_or_city)
        val close = holder?.getView<ImageView>(R.id.iv_close_select_shop_city)
        close?.setOnClickListener {
            dismiss()
        }
        rvSelectAdapter = object :
            BaseQuickAdapter<PoiContentList, BaseViewHolder>(R.layout.item_dialog_account_select_city_or_shop),
            LoadMoreModule {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun convert(holder: BaseViewHolder, item: PoiContentList) {
                var imgShopOrCity = holder.getView<ImageView>(R.id.img_shop_or_city)
                holder.setText(R.id.txt_shop_or_city, item.name)
                if (item.isSelect) {
                    imgShopOrCity.setImageDrawable(resources.getDrawable(R.drawable.ic_spu_true))
                } else {
                    imgShopOrCity.setImageDrawable(resources.getDrawable(R.drawable.ic_spu_fase1))
                }
            }
        }

        rvSelect?.adapter = rvSelectAdapter
        rvSelectAdapter.setOnItemClickListener { adapter, view, position ->
            var arrayList = ArrayList<ShopPoiDto>()
            for (itemDto in rvSelectAdapter.data) {
                itemDto.isSelect = false
            }
            (rvSelectAdapter.data[position] as PoiContentList).isSelect = true
            for (item in rvSelectAdapter.data) {
                if (item.isSelect) {
                    isSelect = item.name.toString()
                    arrayList.add(ShopPoiDto(poiIds = item.id))
                }
            }
            rvSelectAdapter.notifyDataSetChanged()
            okSelectItemClickLister?.invoke(
                arrayList,
                isSelect,
            )
        }
        initData()
    }


    fun initData() {
        var createSelectPoiDto = BaseLiveData<CreateSelectPoiDto>()
        BaseViewModel(Application()).request(
            { accountService.getPoiList(1, "100") }, createSelectPoiDto
        )
        createSelectPoiDto.onSuccess.observe(this) {
            var poiList = ArrayList<PoiContentList>()
            poiList.addAll(it.content as MutableList<PoiContentList>)
            for (item in poiList) {
                if (type == item.name) {
                    isSelect = item.name.toString()
                    item.isSelect = true
                    break
                }
            }
            rvSelectAdapter.setList(poiList)
            rvSelectAdapter.notifyDataSetChanged()
        }
        createSelectPoiDto.onStart.observe(this) {}
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