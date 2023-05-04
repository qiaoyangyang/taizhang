package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.AccountCityOrShopDto
import com.meiling.common.network.data.AccountItemSelect
import com.meiling.oms.databinding.ActivityAccountCreatBinding
import com.meiling.oms.dialog.AccountSelectDialog
import com.meiling.oms.dialog.AccountSelectShopOrCityDialog
import com.meiling.oms.viewmodel.AccountViewModel
import com.meiling.oms.widget.setSingleClickListener


class AccountNewCreateActivity : BaseActivity<AccountViewModel, ActivityAccountCreatBinding>() {

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityAccountCreatBinding {
        return ActivityAccountCreatBinding.inflate(layoutInflater)

    }

    var selectRole = "1"
    var selectAuthWay = "1"
    override fun initListener() {
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

        mDatabind.txtDeliveryStore.setSingleClickListener {
            if (selectAuthWay == "1") {
                var arrayLiatDto = ArrayList<AccountCityOrShopDto>()
                arrayLiatDto.add(AccountCityOrShopDto("1", "店铺1"))
                arrayLiatDto.add(AccountCityOrShopDto("2", "店铺2"))
                var accountSelectDialog =
                    AccountSelectShopOrCityDialog().newInstance("授权发货门店", arrayLiatDto)
                accountSelectDialog.show(supportFragmentManager)
            } else {
                var arrayLiatDto = ArrayList<AccountCityOrShopDto>()
                arrayLiatDto.add(AccountCityOrShopDto("1", "西安"))
                arrayLiatDto.add(AccountCityOrShopDto("2", "上海"))
                var accountSelectDialog =
                    AccountSelectShopOrCityDialog().newInstance("地址授权发货门店", arrayLiatDto)
                accountSelectDialog.show(supportFragmentManager)
            }
        }
    }

}