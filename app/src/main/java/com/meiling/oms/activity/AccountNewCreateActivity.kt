package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.AccountCityOrShopDto
import com.meiling.common.network.data.AccountItemSelect
import com.meiling.common.network.data.ReqCreateAccount
import com.meiling.common.network.data.RequestAccount
import com.meiling.oms.databinding.ActivityAccountCreatBinding
import com.meiling.oms.dialog.AccountSelectCityDialog
import com.meiling.oms.dialog.AccountSelectDialog
import com.meiling.oms.dialog.AccountSelectShopOrCityDialog
import com.meiling.oms.viewmodel.AccountViewModel
import com.meiling.oms.widget.setSingleClickListener


class AccountNewCreateActivity : BaseActivity<AccountViewModel, ActivityAccountCreatBinding>() {

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {

    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityAccountCreatBinding {
        return ActivityAccountCreatBinding.inflate(layoutInflater)

    }

    var selectRole = "1"
    var selectAuthWay = "1"
    override fun initListener() {
        mDatabind.txtAuthWay.text = "按发货门店授权"
        mDatabind.txtSelectRole.setSingleClickListener {
            var arrayLiatDto = ArrayList<AccountItemSelect>()
            arrayLiatDto.add(AccountItemSelect("1", "管理员"))
            var accountSelectDialog = AccountSelectDialog().newInstance("角色", arrayLiatDto)
            accountSelectDialog.show(supportFragmentManager)
            accountSelectDialog.setOkClickItemLister { id, name ->
                mDatabind.txtSelectRole.text = name
                selectRole = id
            }
        }
        mDatabind.txtAuthWay.setSingleClickListener {
            var arrayLiatDto = ArrayList<AccountItemSelect>()
            arrayLiatDto.add(AccountItemSelect("1", "按发货门店授权"))
            arrayLiatDto.add(AccountItemSelect("2", "按城市授权"))
            var accountSelectDialog = AccountSelectDialog().newInstance("授权方式", arrayLiatDto)
            accountSelectDialog.show(supportFragmentManager)
            accountSelectDialog.setOkClickItemLister { id, name ->
                mDatabind.txtAuthWay.text = name
                selectAuthWay = id
            }
        }

        var cityPoiDtoList = ArrayList<ReqCreateAccount.CityPoiDto>()
        mDatabind.txtDeliveryStore.setSingleClickListener {
            if (selectAuthWay == "1") {
                var accountSelectDialog =
                    AccountSelectShopOrCityDialog().newInstance("授权发货门店")
                accountSelectDialog.show(supportFragmentManager)
                accountSelectDialog.setOkClickItemLister { id, name ->  }
            } else {
                var accountSelectDialog =
                    AccountSelectCityDialog().newInstance("地址授权发货门店")
                accountSelectDialog.show(supportFragmentManager)
            }
        }

        mDatabind.txtCreateSave.setSingleClickListener {

            mViewModel.saveAndUpdate(
                ReqCreateAccount(
                    phone = "",
                    username = "",
                    nickname = "",
                    shopPoiDtoList = arrayListOf(),
                    cityPoiDtoList = arrayListOf(),
                    poiShopIds = "",
                    status = 1,
                    authStorePoiAll = "0",
                    roleId = ""
                )
            )
        }
    }

}