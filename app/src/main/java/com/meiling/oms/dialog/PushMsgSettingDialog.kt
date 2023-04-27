package com.meiling.oms.dialog

import android.view.Gravity
import android.widget.ImageView
import com.hjq.shape.view.ShapeButton
import com.meiling.common.base.WheelItemView
import com.meiling.common.utils.PlayNumber
import com.meiling.oms.R
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder


@Suppress("DEPRECATION")
class PushMsgSettingDialog : BaseNiceDialog() {
    init {
        setGravity(Gravity.BOTTOM)
        setOutCancel(false)
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_msg_setting
    }

    fun newInstance(): PushMsgSettingDialog {
        return PushMsgSettingDialog()
    }

    var shopBean = ArrayList<PlayNumber>()

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        var settingView = holder?.getView<WheelItemView>(R.id.wheel_view_left)
        var btn_ok_exit = holder?.getView<ShapeButton>(R.id.btn_ok_select_time)
        var close = holder?.getView<ImageView>(R.id.iv_close_select_shop)
        shopBean.add(PlayNumber("1", "播放一次"))
        shopBean.add(PlayNumber("2", "播放二次"))
        shopBean.add(PlayNumber("3", "播放三次"))
        btn_ok_exit?.setOnClickListener {
            var shop = shopBean[settingView?.selectedIndex!!]
            okSettingSelectClickLister?.invoke(
                shop.id,
                shop.name,
            )
            dismiss()
        }
        close?.setOnClickListener {
            dismiss()
        }
        if (shopBean.size != 0) {
            loadData(settingView!!, shopBean)
        }
    }

    private fun loadData(wheelItemView: WheelItemView, label: ArrayList<PlayNumber>) {
        val items = arrayOfNulls<PlayNumber>(label.size)
        label.forEachIndexed { index, shopBean ->
            items[index] = shopBean
        }
        wheelItemView.setItems(items)
    }

    var okSettingSelectClickLister: ((id: String, name: String) -> Unit)? = null
    fun setOkClickLister(okClickLister: (id: String, name: String) -> Unit) {
        this.okSettingSelectClickLister = okClickLister
    }
}