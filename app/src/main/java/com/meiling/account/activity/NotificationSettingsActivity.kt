package com.meiling.account.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.activity.BaseActivity
import com.meiling.common.utils.NotificationDto
import com.meiling.common.utils.SpannableUtils
import com.meiling.account.R
import com.meiling.account.databinding.ActivityNotificationSettingsBinding
import com.meiling.account.dialog.PushMsgSettingDialog
import com.meiling.account.dialog.PushMsgSettingTimeDialog
import com.meiling.account.jpush.jpushPlay.MessageData
import com.meiling.account.jpush.jpushPlay.MessageManagement
import com.meiling.account.viewmodel.NotificationSettingsViewModel
import com.meiling.account.widget.showToast

//通知设置
class NotificationSettingsActivity :
    BaseActivity<NotificationSettingsViewModel, ActivityNotificationSettingsBinding>() {

    private lateinit var notificationSettingsAdapter: BaseQuickAdapter<NotificationDto?, BaseViewHolder>

    override fun initView(savedInstanceState: Bundle?) {
        initRecycleView()
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityNotificationSettingsBinding {
        return ActivityNotificationSettingsBinding.inflate(layoutInflater)
    }

    override fun initData() {
    }

    private fun initRecycleView() {

        notificationSettingsAdapter =
            object :
                BaseQuickAdapter<NotificationDto?, BaseViewHolder>(R.layout.item_notification_set),
                LoadMoreModule {
                override fun convert(
                    holder: BaseViewHolder, item: NotificationDto?
                ) {
                    holder.setText(R.id.tv_name, item?.name)
                    var txtSettingTime = holder.getView<TextView>(R.id.txt_yu_name)
                    var view = holder.getView<View>(R.id.view_line)
                    var setting = holder.getView<TextView>(R.id.txt_time_setting)
                    holder.setText(R.id.txt_play_num, "播放${item?.num}次数")

                    if (holder.layoutPosition == itemCount - 1) {
                        view.visibility = View.GONE
                    }

                    when (item?.type) {
                        "1" -> {
                            setting.visibility = View.GONE
                            txtSettingTime.visibility = View.GONE
                        }
                        "2" -> {
                            setting.visibility = View.VISIBLE
                            txtSettingTime.visibility = View.VISIBLE
                            txtSettingTime.text = "预定单距离收货时间${item.time}时，提醒"
                            SpannableUtils.setTextcolor(
                                context,
                                txtSettingTime.text.toString(),
                                txtSettingTime,
                                9, 9 + item.time.length,
                                R.color.red
                            )
                        }
                        "3" -> {
                            setting.visibility = View.VISIBLE
                            txtSettingTime.visibility = View.VISIBLE
                            txtSettingTime.text = "发起配送15分钟无配送员/快递员接单，提醒"
                            SpannableUtils.setTextcolor(
                                context,
                                txtSettingTime.text.toString(),
                                txtSettingTime,
                                4, 4 + item.time.length,
                                R.color.red
                            )
                        }
                    }
                }
            }

//        mDatabind.ryOrderLeft.layoutManager = LinearLayoutManager(this)
//        var recyclerViewDivider = RecyclerViewDivider()
//        recyclerViewDivider.setColor(Color.parseColor("#F5F5F5"))
//        recyclerViewDivider.setDividerHeight(1)
//        mDatabind.ryOrderLeft.addItemDecoration(recyclerViewDivider)
        mDatabind.ryOrderLeft.adapter = notificationSettingsAdapter
        notificationSettingsAdapter.setList(arrayListOf())
        notificationSettingsAdapter.setEmptyView(R.layout.empty_want_goods_recycler)
//        notificationSettingsAdapter.setOnItemClickListener { adapter, view, position ->
//        }

        var list = ArrayList<NotificationDto>()
        list.add(NotificationDto("新订单", "1", "1", "120"))
        list.add(NotificationDto("订单取消", "1", "1", "120"))
        list.add(NotificationDto("订单退款", "1", "1", "120"))
        list.add(NotificationDto("配送接单", "1", "1", "120"))
        list.add(NotificationDto("配送取消", "1", "1", "120"))
        list.add(NotificationDto("配送完成", "1", "1", "120"))
        list.add(NotificationDto("预定单", "2", "2", "90"))
        list.add(NotificationDto("配送超时", "3", "3", "80"))
        notificationSettingsAdapter.setList(list)
        notificationSettingsAdapter.addChildClickViewIds(R.id.txt_time_setting, R.id.txt_play_num);

        notificationSettingsAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.txt_play_num -> {
                    adapter.data[position]
                    when (position) {
                        0 -> {
                            showToast((adapter.data[position] as NotificationDto).name)
                            MessageManagement.get(this)?.addMessage(
                                MessageData(
                                    R.raw.new_order,
                                    ""
                                ), 2
                            )
                            var newInstance = PushMsgSettingDialog().newInstance()
                            newInstance.show(supportFragmentManager)
                            newInstance.setOkClickLister { id, name ->
                                list[0] = NotificationDto("新订单", id, "1", "120")
                                showToast(id)
                                notificationSettingsAdapter.setList(list)
                                adapter.notifyDataSetChanged()
                            }

                        }
                        1 -> {
                            showToast((adapter.data[position] as NotificationDto).name)
                            MessageManagement.get(this)?.addMessage(
                                MessageData(
                                    R.raw.cancel_order,
                                    ""
                                ), 3
                            )
                            var newInstance = PushMsgSettingDialog().newInstance()
                            newInstance.show(supportFragmentManager)
                            newInstance.setOkClickLister { id, name ->
                                list[0] = NotificationDto("新订单", id, "1", "120")
                                showToast(id)
                                adapter.notifyDataSetChanged()
                            }
                        }
                        2 -> {
                            showToast((adapter.data[position] as NotificationDto).name)
                            MessageManagement.get(this)?.addMessage(
                                MessageData(
                                    R.raw.refund_order,
                                    ""
                                ), 1
                            )
                            var newInstance = PushMsgSettingDialog().newInstance()
                            newInstance.show(supportFragmentManager)
                            newInstance.setOkClickLister { id, name ->
                                list[0] = NotificationDto("新订单", id, "1", "120")
                                showToast(id)
                                adapter.notifyDataSetChanged()
                            }
                        }
                        3 -> {
                            showToast((adapter.data[position] as NotificationDto).name)
                            MessageManagement.get(this)?.addMessage(
                                MessageData(
                                    R.raw.order_dis_jiedan,
                                    ""
                                ), 1
                            )
                            var newInstance = PushMsgSettingDialog().newInstance()
                            newInstance.show(supportFragmentManager)
                            newInstance.setOkClickLister { id, name ->
                                list[0] = NotificationDto("新订单", id, "1", "120")
                                showToast(id)
                                adapter.notifyDataSetChanged()
                            }
                        }
                        4 -> {
                            showToast((adapter.data[position] as NotificationDto).name)
                            MessageManagement.get(this)?.addMessage(
                                MessageData(
                                    R.raw.order_dis_jiedan,
                                    ""
                                ), 1
                            )
                            var newInstance = PushMsgSettingDialog().newInstance()
                            newInstance.show(supportFragmentManager)
                            newInstance.setOkClickLister { id, name ->
                                list[0] = NotificationDto("新订单", id, "1", "120")
                                showToast(id)
                                adapter.notifyDataSetChanged()
                            }
                        }
                        5 -> {
                            showToast((adapter.data[position] as NotificationDto).name)
                            MessageManagement.get(this)?.addMessage(
                                MessageData(
                                    R.raw.order_dis_jiedan,
                                    ""
                                ), 1
                            )
                            var newInstance = PushMsgSettingDialog().newInstance()
                            newInstance.show(supportFragmentManager)
                            newInstance.setOkClickLister { id, name ->
                                list[0] = NotificationDto("新订单", id, "1", "120")
                                showToast(id)
                                adapter.notifyDataSetChanged()
                            }
                        }
                        6 -> {
                            showToast((adapter.data[position] as NotificationDto).name)
                            MessageManagement.get(this)?.addMessage(
                                MessageData(
                                    R.raw.order_dis_jiedan,
                                    ""
                                ), 1
                            )
                            var newInstance = PushMsgSettingDialog().newInstance()
                            newInstance.show(supportFragmentManager)
                            newInstance.setOkClickLister { id, name ->
                                list[0] = NotificationDto("新订单", id, "1", "120")
                                showToast(id)
                                adapter.notifyDataSetChanged()
                            }
                        }
                        7 -> {
                            MessageManagement.get(this)?.addMessage(
                                MessageData(
                                    R.raw.order_dis_jiedan,
                                    ""
                                ), 1
                            )
                            var newInstance = PushMsgSettingDialog().newInstance()
                            newInstance.show(supportFragmentManager)
                            newInstance.setOkClickLister { id, name ->
                                list[0] = NotificationDto("新订单", id, "1", "120")
                                showToast(id)
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }

                }
                R.id.txt_time_setting -> {
                    when (position) {
                        6 -> {
                            var pushMsgSettingTimeDialog = PushMsgSettingTimeDialog().newInstance()
                            pushMsgSettingTimeDialog.show(supportFragmentManager)
                            pushMsgSettingTimeDialog.setOkClickLister { time, channel ->
                                showToast("====STime${time}")
                            }
                        }
                        7 -> {
                            var pushMsgSettingTimeDialog = PushMsgSettingTimeDialog().newInstance()
                            pushMsgSettingTimeDialog.show(supportFragmentManager)
                            pushMsgSettingTimeDialog.setOkClickLister { time, channel ->
                                showToast("====STime${time}")
                            }
                            showToast("设置时间2")
                        }
                    }

                }
            }

        }


    }

    override fun createObserver() {
        super.createObserver()
    }
}