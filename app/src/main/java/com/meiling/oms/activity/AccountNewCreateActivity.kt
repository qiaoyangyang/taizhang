package com.meiling.oms.activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.meiling.common.activity.BaseActivity
import com.meiling.common.constant.SPConstants
import com.meiling.common.network.data.AccountItemSelect
import com.meiling.common.network.data.CityPoiDto
import com.meiling.common.network.data.ReqCreateAccount
import com.meiling.common.network.data.ShopPoiDto
import com.meiling.common.utils.MMKVUtils
import com.meiling.oms.databinding.ActivityAccountCreatBinding
import com.meiling.oms.dialog.AccountSelectCityDialog
import com.meiling.oms.dialog.AccountSelectDialog
import com.meiling.oms.dialog.AccountSelectShopOrCityDialog
import com.meiling.oms.viewmodel.AccountViewModel
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast


class AccountNewCreateActivity : BaseActivity<AccountViewModel, ActivityAccountCreatBinding>() {

    var adminUserIdEdit = ""
    override fun initView(savedInstanceState: Bundle?) {
        var intentEdit = intent.getBooleanExtra("edit", false)
        mViewModel.roleList()
        if (intentEdit) {
            var adminViewId = intent.getStringExtra("adminViewId")
            adminUserIdEdit = adminViewId.toString()
            mViewModel.userDetail(adminViewId.toString())
        }
    }

    override fun initData() {

    }


    var cityPoiDtoList = ArrayList<CityPoiDto>()
    var shopPoiDtoList = ArrayList<ShopPoiDto>()

    override fun getBind(layoutInflater: LayoutInflater): ActivityAccountCreatBinding {
        return ActivityAccountCreatBinding.inflate(layoutInflater)

    }

    var selectRole = "19"
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
                mDatabind.txtDeliveryStore.text = ""
                if (adminUserIdEdit.isNullOrBlank()) {
                    mDatabind.txtDeliveryStore.text = ""
                } else {
                    if (selectAuthWay == "1") {
                        mDatabind.txtDeliveryStore.text = "选中${shopPoiDtoList.size}个门店"
                    }
                    if (selectAuthWay == "0") {
                        mDatabind.txtDeliveryStore.text = "选中${cityPoiDtoList.size}个门店"
                    }
                }
            }
        }


        var authStorePoiAll = "0"
        mDatabind.txtDeliveryStore.setSingleClickListener {
            if (selectAuthWay == "1") {
                var accountSelectDialog = AccountSelectShopOrCityDialog().newInstance(
                    "授权发货门店",
                    adminUserIdEdit,
                    shopPoiDtoList
                )
                accountSelectDialog.show(supportFragmentManager)
                accountSelectDialog.setOkClickItemLister { arrayList, isSelectAll ->
                    shopPoiDtoList.clear()
                    cityPoiDtoList.clear()
                    shopPoiDtoList.addAll(arrayList)
                    if (isSelectAll == "1") {
                        authStorePoiAll = "1"
                        mDatabind.txtDeliveryStore.text = "选中全部门店"
                    } else {
                        authStorePoiAll = "0"
                        mDatabind.txtDeliveryStore.text = "选中${arrayList.size}个门店"
                    }
                }
            } else {
                var accountSelectCityDialog =
                    AccountSelectCityDialog().newInstance("授权发货门店",adminUserIdEdit,cityPoiDtoList)
                accountSelectCityDialog.show(supportFragmentManager)
                accountSelectCityDialog.setOkClickItemCityLister { cityDialogList, selectNum ->
                    Log.d("lwq", "=====${cityDialogList}")
                    shopPoiDtoList.clear()
                    cityPoiDtoList.clear()
                    cityPoiDtoList.addAll(cityDialogList)
                    mDatabind.txtDeliveryStore.text = "$selectNum"
                }

            }
        }

        mDatabind.txtCreateSave.setSingleClickListener {
            if (mDatabind.edtInputAccount.text.toString().trim().isBlank()) {
                showToast("必填信息未填写，请检查后提交")
                return@setSingleClickListener
            }
//            mViewModel.checkAccount(mDatabind.edtInputPhone.toString().trim())

            if (mDatabind.edtInputPhone.text.toString().trim().isBlank()) {
                showToast("必填信息未填写，请检查后提交")
                return@setSingleClickListener
            }


            if (!isPhoneNumber(mDatabind.edtInputPhone.text.toString().trim())) {
                showToast("手机号格式错误提示")
                return@setSingleClickListener
            }

            if (mDatabind.edtInputName.text.toString().trim().isBlank()) {
                showToast("必填信息未填写，请检查后提交")
                return@setSingleClickListener
            }
//            mViewModel.checkAccountPhone(mDatabind.edtInputPhone.toString().trim())

            if (selectAuthWay == "1") {
                if (authStorePoiAll == "0" && shopPoiDtoList.isEmpty()) {
                    showToast("请选择授权门店")
                    return@setSingleClickListener
                }
            } else {
                if (cityPoiDtoList.isNullOrEmpty()) {
                    showToast("请选择授权城市")
                    return@setSingleClickListener
                }
            }
            mViewModel.saveAndUpdate(
                ReqCreateAccount(
                    adminUserId = adminUserIdEdit,
                    phone = mDatabind.edtInputPhone.text.toString().trim(),
                    username = mDatabind.edtInputAccount.text.toString().trim(),
                    nickname = mDatabind.edtInputName.text.toString().trim(),
                    shopPoiDtoList = shopPoiDtoList,
                    cityPoiDtoList = cityPoiDtoList,
                    poiShopIds = "",
                    status = 1,
                    authStorePoiAll = authStorePoiAll,
                    roleId = selectRole
                )
            )
        }
    }


    override fun createObserver() {
        mViewModel.disableAccount.onStart.observe(this) {
            showLoading("请求中")

        }
        mViewModel.disableAccount.onSuccess.observe(this) {
            showToast("账号信息保存成功")
            disLoading()
            finish()
        }
        mViewModel.disableAccount.onError.observe(this) {
            disLoading()
            showToast(it.msg)
        }

        mViewModel.roleListDto.onStart.observe(this) {
            showLoading("请求中")
        }
        mViewModel.roleListDto.onSuccess.observe(this) {
            disLoading()
            selectRole = it[0].viewId.toString()
        }
        mViewModel.roleListDto.onError.observe(this) {
            disLoading()
            showToast(it.msg)
        }

        mViewModel.accountDetailDto.onStart.observe(this) {
            showLoading("请求中")
        }
        mViewModel.accountDetailDto.onSuccess.observe(this) {
            disLoading()
            mDatabind.edtInputPhone.setText(it.adminUser?.phone)
            mDatabind.edtInputAccount.setText(it.adminUser?.username)
            mDatabind.edtInputName.setText(it.adminUser?.nickname)
            mDatabind.txtSelectRole.text = it.roleName
            if (!it.shopPoiVoList.isNullOrEmpty()) {
                selectAuthWay = "1"
                mDatabind.txtAuthWay.text = "按发货门店授权"
                shopPoiDtoList.addAll(it.shopPoiVoList)
                mDatabind.txtDeliveryStore.text = "选中${shopPoiDtoList.size}个门店"
            }

            if (!it.cityPoiVoList.isNullOrEmpty()) {
                selectAuthWay = "0"
                mDatabind.txtAuthWay.text = "按城市授权"
                cityPoiDtoList.addAll(it.cityPoiVoList)
                mDatabind.txtDeliveryStore.text = "选中${cityPoiDtoList.size}个城市"
            }

        }
        mViewModel.accountDetailDto.onError.observe(this) {
            disLoading()
            showToast(it.msg)
        }

    }

    private fun isPhoneNumber(input: String): Boolean {
        val regex = Regex("^1[3-9]\\d{9}$")
        return regex.matches(input)
    }
}