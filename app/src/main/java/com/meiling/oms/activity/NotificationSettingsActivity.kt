package com.meiling.oms.activity

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hjq.shape.view.ShapeTextView
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.WriteoffhistoryPageData
import com.meiling.common.utils.Appdata
import com.meiling.common.utils.RecyclerViewDivider
import com.meiling.common.utils.SpannableUtils
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityNotificationSettingsBinding
import com.meiling.oms.viewmodel.NotificationSettingsViewModel

//通知设置
class NotificationSettingsActivity :
    BaseActivity<NotificationSettingsViewModel, ActivityNotificationSettingsBinding>() {

    lateinit var NotificationSettingsAdapter: BaseQuickAdapter<String?, BaseViewHolder>

    override fun initView(savedInstanceState: Bundle?) {
        initRecycleyView()
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityNotificationSettingsBinding {
        return ActivityNotificationSettingsBinding.inflate(layoutInflater)
    }
    override fun initData() {


        NotificationSettingsAdapter.setList(Appdata.getet())
    }

    private fun initRecycleyView() {

        NotificationSettingsAdapter = object :
            BaseQuickAdapter<String?, BaseViewHolder>(R.layout.item_notification_set),
            LoadMoreModule {
            override fun convert(
                holder: BaseViewHolder,
                item: String?

            ) {
                holder.setText(R.id.tv_name,item)



//
//

            }
        }

        mDatabind.ryOrderLeft.layoutManager = LinearLayoutManager(this)
        var recyclerViewDivider = RecyclerViewDivider()
        recyclerViewDivider.setColor(Color.parseColor("#F5F5F5"))
        recyclerViewDivider.setDividerHeight(1)
        mDatabind.ryOrderLeft.addItemDecoration(recyclerViewDivider)
        mDatabind.ryOrderLeft.adapter = NotificationSettingsAdapter
        NotificationSettingsAdapter.setList(arrayListOf())
        NotificationSettingsAdapter.setEmptyView(R.layout.empty_want_goods_recycler)
        NotificationSettingsAdapter.setOnItemClickListener { adapter, view, position ->

        }


    }

    override fun createObserver() {
        super.createObserver()
    }
}