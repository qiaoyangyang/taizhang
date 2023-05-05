package com.meiling.oms.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hjq.shape.view.ShapeTextView
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.AccountListContentDto
import com.meiling.common.network.data.RequestAccount
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityAccountManagerBinding
import com.meiling.oms.dialog.MineExitDialog
import com.meiling.oms.viewmodel.AccountViewModel
import com.meiling.oms.widget.*


class AccountManagerActivity : BaseActivity<AccountViewModel, ActivityAccountManagerBinding>() {

    var pageIndex = 1

    lateinit var accountListAdapter: BaseQuickAdapter<AccountListContentDto, BaseViewHolder>


    var isPosition = 0
    var disableTip = ""


    override fun initView(savedInstanceState: Bundle?) {
        accountListAdapter = object :
            BaseQuickAdapter<AccountListContentDto, BaseViewHolder>(R.layout.item_account_manager),
            LoadMoreModule {
            override fun convert(holder: BaseViewHolder, item: AccountListContentDto) {
                holder.setText(R.id.txt_account_name, item.nickname)
                holder.setText(R.id.txt_account_role, item.roleName)
                var accountSetting = holder.getView<ShapeTextView>(R.id.txt_account_setting)
                var accountEdit = holder.getView<ShapeTextView>(R.id.txt_account_edit)
                var ivAccount = holder.getView<ImageView>(R.id.iv_account_at)

                if (item.isNow) {
                    ivAccount.visibility = View.VISIBLE
                    ivAccount.setImageResource(R.mipmap.icon_account_at)
                } else {
                    if (item.status == 9) {
                        ivAccount.visibility = View.VISIBLE
                        ivAccount.setImageResource(R.mipmap.icon_account_disable)
                    } else {
                        ivAccount.visibility = View.GONE
                    }
                }

                when (item.status) {
                    1 -> {
                        if (item.isNow) {
                            accountSetting.text = "禁用"
                            accountSetting.setTextColor(Color.parseColor("#C4C4C4"))
//                            accountSetting.shapeDrawableBuilder.setStrokeColor(Color.parseColor("#E0E0E0"))
//                                .intoBackground()
                            accountSetting.shapeDrawableBuilder.setStrokeColor(Color.parseColor("#C4C4C4"))
                                .intoBackground()
                            accountSetting.isClickable = false
                        } else {
                            accountSetting.text = "禁用"
                            accountSetting.setTextColor(Color.parseColor("#FD4B48"))
                            accountSetting.shapeDrawableBuilder.setStrokeColor(Color.parseColor("#FD4B48"))
                                .intoBackground()
                            accountSetting.setOnClickListener {
                                isPosition = getItemPosition(item)
                                val dialog: MineExitDialog =
                                    MineExitDialog().newInstance(
                                        "温馨提示",
                                        "禁用后,账号将无法继续登录系统。\n 请确认是否禁用该账号？",
                                        "取消",
                                        "确认",
                                        false
                                    )
                                dialog.setOkClickLister {
                                    dialog.dismiss()
                                    disableTip = "该账号已禁用"
                                    mViewModel.setDisableAccount(item.viewId, "9")
                                }
                                dialog.show(supportFragmentManager)
                            }
                        }

                    }
                    9 -> {
                        accountSetting.isClickable = true
                        accountSetting.text = "启用"
                        accountSetting.setTextColor(Color.parseColor("#FD4B48"))
                        accountSetting.shapeDrawableBuilder.setStrokeColor(Color.parseColor("#FD4B48"))
                            .intoBackground()
                        accountSetting.setOnClickListener {
                            val dialog: MineExitDialog =
                                MineExitDialog().newInstance(
                                    "温馨提示",
                                    "启用后，账号将可以正常登录系统。\n 请确认是否启用该账号？",
                                    "取消",
                                    "确认",
                                    false
                                )
                            dialog.setOkClickLister {
                                dialog.dismiss()
                                disableTip = "该账号已启用"
                                mViewModel.setDisableAccount(item.viewId, "1")
                            }
                            dialog.show(supportFragmentManager)
                        }
                    }
                    -1 -> {}

                }
                accountEdit.setOnClickListener {
                    showToast("编辑")
                }

            }
        }
        mDatabind.rvAccountList.adapter = accountListAdapter

    }

    override fun initData() {
        initViewRequest()
    }


    private fun initViewRequest() {
        mViewModel.getAccountList(RequestAccount(pageIndex, 10))
        accountListAdapter.loadMoreModule.loadMoreView = SS()
        accountListAdapter.loadMoreModule.setOnLoadMoreListener {
            pageIndex++
            mViewModel.getAccountList(RequestAccount(pageIndex, 10))
        }
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityAccountManagerBinding {
        return ActivityAccountManagerBinding.inflate(layoutInflater)

    }

    override fun initListener() {
        mDatabind.txtCreateNewAccount.setSingleClickListener {
            startActivity(Intent(this, AccountNewCreateActivity::class.java))
        }
    }

    override fun createObserver() {
        mViewModel.accountListDto.onStart.observe(this) {
            showLoading("加载中")
        }
        mViewModel.accountListDto.onSuccess.observe(this) {
            disLoading()
            if (it.pageIndex == 1) {
                if (it.content.isNullOrEmpty()) {
                    accountListAdapter.setList(null)
                } else {
                    accountListAdapter.setList(it.content as MutableList<AccountListContentDto>)
                    accountListAdapter.notifyDataSetChanged()
                }
            } else {
                accountListAdapter.addData(it.content as MutableList<AccountListContentDto>)
                accountListAdapter.notifyDataSetChanged()
            }
            if (it.content!!.size < 10) {
                disLoading()
                accountListAdapter.footerWithEmptyEnable = false
                accountListAdapter.footerLayout?.visibility = View.GONE
                accountListAdapter.loadMoreModule.loadMoreEnd()
                accountListAdapter.notifyDataSetChanged()
            } else {
                accountListAdapter.loadMoreModule.loadMoreComplete()
            }

        }
        mViewModel.accountListDto.onError.observe(this) {
            disLoading()
            showToast(it.msg)
        }
        mViewModel.disableAccount.onStart.observe(this) {
            showLoading("请求中")
        }
        mViewModel.disableAccount.onSuccess.observe(this) {
            disLoading()
            pageIndex = 1
            initViewRequest()
            showToast(disableTip)
        }
        mViewModel.disableAccount.onError.observe(this) {
            disLoading()
            showToast(it.msg)
        }

    }

}