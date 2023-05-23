package com.meiling.oms.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.Nullable
import com.alibaba.android.arouter.launcher.ARouter
import com.amap.api.services.core.PoiItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.codbking.widget.DatePickDialog
import com.codbking.widget.bean.DateType
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import com.meiling.common.GlideApp
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.*
import com.meiling.common.utils.GlideAppUtils
import com.meiling.common.utils.PermissionUtilis
import com.meiling.common.utils.SoftKeyBoardListener
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityOrderCreatBinding
import com.meiling.oms.dialog.AccountSelectDialog
import com.meiling.oms.dialog.ShopDialog
import com.meiling.oms.viewmodel.OrderCreateViewModel
import com.meiling.oms.widget.KeyBoardUtil
import com.meiling.oms.widget.formatCurrentDateToString
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast
import java.math.BigDecimal
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList


class OrderCreateActivity : BaseActivity<OrderCreateViewModel, ActivityOrderCreatBinding>() {

    private val REQUEST_CODE = 1002
    private val ACCESS_FINE_LOCATION = 1

    var goodsList = ArrayList<OrderCreateGoodsDto>()
    var goodsArrayList = ArrayList<OrderCreateGoodsDto1>()

    var shopBeanList = ArrayList<ShopBean>()
    lateinit var orderGoodsAdapter: BaseQuickAdapter<OrderCreateGoodsDto, BaseViewHolder>

    override fun initView(savedInstanceState: Bundle?) {
        goodsList.add(
            OrderCreateGoodsDto(
                name = "默认商品",
                num = 1,
                "1",
                totalPrice = "",
                "默认规格",
                imgUrl = "https://static.igoodsale.com/default-goods.png"
            )
        )
        orderGoodsAdapter = object :
            BaseQuickAdapter<OrderCreateGoodsDto, BaseViewHolder>(R.layout.item_create_order_goods) {
            override fun convert(holder: BaseViewHolder, item: OrderCreateGoodsDto) {
                var goodsName = holder.getView<EditText>(R.id.txt_order_goods_name)
                var price = holder.getView<EditText>(R.id.txt_order_goods_price)
                var num = holder.getView<EditText>(R.id.edt_add_num)
                var selectImg = holder.getView<ImageView>(R.id.img_order_goods_select)
                var add = holder.getView<ImageView>(R.id.edt_add_jia)
                var sub = holder.getView<ImageView>(R.id.edt_add_jian)
                var icon = holder.getView<ImageView>(R.id.img_order_goods_icon)
                GlideAppUtils.loadUrl(icon, item.imgUrl)
                num.setText("${item.num}")
                price.setText(item.salePrice)
                goodsName.setText(item.name)
                num.setSelection(item.num.toString().length)
                if (item.num > 1) {
                    sub.setImageDrawable(resources.getDrawable(R.drawable.icon_goods_subtract_true))
                } else {
                    sub.setImageDrawable(resources.getDrawable(R.drawable.icon_goods_subtract_false))
                }

                if (item.num >= 999) {
                    add.setImageDrawable(resources.getDrawable(R.drawable.icon_goods_add_false))
                } else {
                    add.setImageDrawable(resources.getDrawable(R.drawable.icon_goods_add_true))
                }
                add.setOnClickListener {
                    if (item.num <= 999) {
                        item.num = item.num + 1
                    } else {
                        item.num = item.num
                    }
                    num.setText(item.num.toString())
                }
                sub.setOnClickListener {
                    if (item.num > 1) {
                        item.num = item.num - 1
                    } else {
                        item.num = item.num
                    }
                    num.setText(item.num.toString())
                }


                if (itemCount == 1) {
                    mDatabind.txtDeleteGoods.visibility = View.GONE
                    selectImg.setImageDrawable(resources.getDrawable(R.drawable.icon_goods_checkbox))
                } else {
                    if (item.isSelectGoods) {
                        selectImg.setImageDrawable(resources.getDrawable(R.drawable.icon_goods_true))
                    } else {
                        selectImg.setImageDrawable(resources.getDrawable(R.drawable.icon_goods_fase))
                    }
                }
                num.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                        // 在文本变化之前的操作，可以不做处理
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
//                        val input = s.toString()
//                        Log.d("TAG", "convert:${item.num} ")
//                        Log.d("TAG", "convert:${input} ")
//
////                        // 如果输入的内容为空，直接返回
//                        if (input.isEmpty()) {
//                            return
//                        }
////
//                        // 检查输入的长度是否超过 3 位
//                        if (input.length > 3) {
//                            num.setText(input.substring(0, 3))
//                            num.setSelection(3)
//                            return
//                        }
//
//                        // 检查输入是否为 00 或 01
//                        if (input == "00") {
//                            num.setText("1")
//                            return
//                        }
//
//                        // 检查输入是否大于 999
//                        val number = input.toIntOrNull()
//                        if (number != null && number > 999) {
//                            num.setText("999")
//                            num.setSelection(3)
//                        }
//
//                        // 检查是否为01或100.22这样的数字
//                        if (input.startsWith("0") && input != "0" ) {
//                            // 不合法的数字格式，禁止输入
//                            val newText = input.substring(0, input.length - 1)
//                            num.setText(newText)
//                            num.setSelection(newText.length)
//                            return
//                        }
//                        item.num = input.toInt()
//                        if (item.num >= 1) {
//                            sub.setImageDrawable(resources.getDrawable(R.drawable.icon_goods_subtract_true))
//                        } else {
//                            sub.setImageDrawable(resources.getDrawable(R.drawable.icon_goods_subtract_false))
//                        }
//                        if (item.num >= 999) {
//                            add.setImageDrawable(resources.getDrawable(R.drawable.icon_goods_add_false))
//                        } else {
//                            add.setImageDrawable(resources.getDrawable(R.drawable.icon_goods_add_true))
//                        }
                    }

                    override fun afterTextChanged(s: Editable?) {
                        val input = s.toString()
                        Log.d("TAG", "convert:${item.num} ")
                        Log.d("TAG", "convert:${input} ")

//                        // 如果输入的内容为空，直接返回
                        if (input.isEmpty()) {
                            return
                        }
//
                        // 检查输入的长度是否超过 3 位
                        if (input.length > 3) {
                            num.setText(input.substring(0, 3))
                            num.setSelection(3)
                            return
                        }

                        // 检查输入是否为 00 或 01
                        if (input == "00"||input=="0") {
                            num.setText("1")
                            return
                        }

                        // 检查输入是否大于 999
                        val number = input.toIntOrNull()
                        if (number != null && number > 999) {
                            num.setText("999")
                            num.setSelection(3)
                        }

                        // 检查是否为01或100.22这样的数字
                        if (input.startsWith("0") && input != "0") {
                            // 不合法的数字格式，禁止输入
                            val newText = input.substring(0, input.length - 1)
                            num.setText(newText)
                            num.setSelection(newText.length)
                            return
                        }
                        item.num = input.toInt()
                        num.setSelection( item.num.toString().length)
                        if (item.num > 1) {
                            sub.setImageDrawable(resources.getDrawable(R.drawable.icon_goods_subtract_true))
                        } else {
                            sub.setImageDrawable(resources.getDrawable(R.drawable.icon_goods_subtract_false))
                        }
                        if (item.num >= 999) {
                            add.setImageDrawable(resources.getDrawable(R.drawable.icon_goods_add_false))
                        } else {
                            add.setImageDrawable(resources.getDrawable(R.drawable.icon_goods_add_true))
                        }
                    }
                })

                num.setOnClickListener {
                    num.isFocusable = true
                    num.isFocusableInTouchMode = true
                    num.requestFocus()
                    num.findFocus()
                    num.setSelection(1)
                    KeyBoardUtil.openKeyBord(num, context)
                }
                num.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
                    if (!hasFocus) {
                        val inputText = num.text.toString()
                        if (inputText.isEmpty()) {
                            num.setText("1")
                            item.num = 1
                            num.setSelection(1)
                            KeyBoardUtil.closeKeyBord(num, context)
                        }
                    }
                }
                num.setOnEditorActionListener { view, actionId, event ->
                    if (actionId == 0 || actionId == 3) {
                        num.isFocusable = false
                        if (num.text.toString().isEmpty()) {
                            num.setText("1")
                            item.num = 1
                            num.setSelection(0)
                            notifyDataSetChanged()
                        }
                    }
                    false
                }
                price.setOnEditorActionListener { view, actionId, event ->
                    if (actionId == 0 || actionId == 3) {
                        // 在这里执行你的逻辑操作
                        if (price.text.toString().isEmpty()) {
                            price.setText("0.01")
//                            price.setSelection(price.text.toString().length)
                            price.isFocusable = false
                            item.salePrice = "0.01"
                        } else if (price.text.toString() == "0.0" || price.text.toString() == "0.00" || price.text.toString() == "0") {
                            price.setText("0.01")
                            price.isFocusable = false
                            item.salePrice = "0.01"
                        }
                    }
                    false
                }
                price.setOnClickListener {
                    price.isFocusable = true
                    price.isFocusableInTouchMode = true
                    price.requestFocus()
                    price.findFocus()
                    price.setSelection(item.salePrice.length)
                    KeyBoardUtil.openKeyBord(price, context)
                }
                price.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
                    if (!hasFocus) {
                        val inputText = price.text.toString()
                        if (inputText.isEmpty()) {
                            price.setText("0.01")
                            KeyBoardUtil.closeKeyBord(price, context)
                        }
                    }
                }
                goodsName.setOnClickListener {
                    goodsName.isFocusable = true
                    goodsName.isFocusableInTouchMode = true
                    goodsName.requestFocus()
                    goodsName.findFocus()
                    goodsName.setSelection(item.name.length)
                    KeyBoardUtil.openKeyBord(goodsName, context)
                }
                goodsName.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
                    if (!hasFocus) {
                        val inputText = goodsName.text.toString()
                        if (inputText.isEmpty()) {
                            goodsName.setText("默认商品")
                            KeyBoardUtil.closeKeyBord(goodsName, context)
                        }
                    }
                }
                goodsName.setOnEditorActionListener { view, actionId, event ->
                    if (actionId == 0 || actionId == 3) {
                        // 键盘消失
                        price.isFocusable = false
                        if (goodsName.text.toString().isEmpty()) {
                            goodsName.setText("默认商品")
                            goodsName.isFocusable = false
                            item.name = "默认商品"
                        }
                    }
                    false
                }
                //键盘隐藏之后数据
                SoftKeyBoardListener.setListener(this@OrderCreateActivity, object :
                    SoftKeyBoardListener.OnSoftKeyBoardChangeListener {
                    override fun keyBoardShow(height: Int) {
                    }

                    override fun keyBoardHide(height: Int) {
                        if (TextUtils.isEmpty(price.text.toString())) {
                            price.setText("0.01")
//                            price.setSelection(price.text.toString().length)
                            item.salePrice = "0.01"
                        } else if (price.text.toString() == "0.0" || price.text.toString() == "0.00" || price.text.toString() == "0") {
                            price.setText("0.01")
                            price.isFocusable = false
                            item.salePrice = "0.01"
                        }
                        price.isFocusable = false
                        num.isFocusable = false
                        goodsName.isFocusable = false
                        if (TextUtils.isEmpty(num.text.toString())) {
                            num.setText("1")
                            num.setSelection(1)
                            item.num = 1

                        }
                        if (TextUtils.isEmpty(goodsName.text.toString())) {
                            goodsName.setText("默认商品")
                            goodsName.setSelection(1)
                        }
                    }
                })
                price.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                        // 在文本变化之前的操作，可以不做处理
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {

                    }

                    override fun afterTextChanged(s: Editable?) {
                        val input = s.toString()
                        // 如果输入的内容为空，直接返回
                        if (input.isEmpty()) {
                            return
                        }

                        // 如果输入的内容为小数点，将其替换为0
                        if (input == ".") {
                            price.setText("0.")
                            price.setSelection(1)
                            item.salePrice = input
                            return
                        }

                        // 检查小数点前的数字是否合法
                        val numberPattern = Pattern.compile("^([1-9]\\d{0,6}|0)(\\.\\d{0,2})?$")
//                        val numberPattern = Pattern.compile("[0-9]{0,5}(\\.[0-9]{0,2})?")
                        val numberMatcher = numberPattern.matcher(input)
                        if (!numberMatcher.matches()) {
                            // 不合法的数字格式，禁止输入
                            val newText = input.substring(0, input.length - 1)
                            price.setText(newText)
                            price.setSelection(price.text.length)
                            item.salePrice = newText
                            return
                        }

                        // 检查小数点前是否满足要求
                        val prefix = input.substringBefore(".")
                        if (prefix.length > 5 || prefix == "00" || prefix == "000" || prefix == "0000" || prefix == "00000") {
                            // 不满足要求，禁止输入
                            val newText = input.substring(0, input.length - 1)
                            price.setText("1")
                            price.setSelection(1)
                            item.salePrice = "1"
                            return
                        }

                        // 检查小数点后的数字是否合法
                        val decimalPattern = Pattern.compile("\\d{0,2}")
                        val decimalMatcher = decimalPattern.matcher(input.substringAfter(".", ""))
                        if (!decimalMatcher.matches()) {
                            // 不合法的小数格式，禁止输入
                            val newText = input.substring(0, input.length - 1)
                            price.setText("0.01")
                            price.setSelection(3)
                            item.salePrice = "0.01"
                            return
                        }

                        // 检查小数点后是否超过两位
                        if (input.substringAfter(".", "").length > 2) {
                            // 超过两位小数，禁止输入第三位小数
                            val newText = input.substring(0, input.length - 1)
                            price.setText(newText)
                            item.salePrice = newText
                            price.setSelection(newText.length)
                            return
                        }

                        // 检查是否为01或100.22这样的数字
                        if (input.startsWith("0") && input != "0" && !input.startsWith("0.")) {
                            // 不合法的数字格式，禁止输入
                            val newText = input.substring(0, input.length - 1)
                            price.setText(newText)
                            item.salePrice = newText
                            price.setSelection(newText.length)
                            return
                        }
                        item.salePrice = input
                    }


                })
                goodsName.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                        // 在文本变化之前的操作，可以不做处理
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {

                    }

                    override fun afterTextChanged(s: Editable?) {
                        val inputText = s.toString()
                        if (inputText.isEmpty()) {
                            return
                        }
                        if (inputText.length > 100) {
                            item.name = inputText.substring(0, s.toString().length - 1)
                        } else {
                            item.name = s.toString() ?: "默认商品"
                        }
//                        item.name = s.toString() ?: "默认商品"
//                        goodsName.setSelection(item.name.toString().length)
                    }
                })
            }
        }
        orderGoodsAdapter.setNewInstance(goodsList)
        mDatabind.rvCreateGoods.adapter = orderGoodsAdapter


        orderGoodsAdapter.setOnItemClickListener { adapter, view, position ->
            if (orderGoodsAdapter.data.size > 1) {
                (adapter.data[position] as OrderCreateGoodsDto).isSelectGoods =
                    !(adapter.data[position] as OrderCreateGoodsDto).isSelectGoods
                orderGoodsAdapter.notifyItemChanged(position)
            }
            if (orderGoodsAdapter.data.count { it.isSelectGoods } > 0) {
                mDatabind.txtDeleteGoods.visibility = View.VISIBLE
            } else {
                mDatabind.txtDeleteGoods.visibility = View.GONE
            }
            orderGoodsAdapter.notifyDataSetChanged()
        }
        mViewModel.getCityPoiOffline()
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityOrderCreatBinding {
        return ActivityOrderCreatBinding.inflate(layoutInflater)
    }

    var selectAuthWay = "1" //1商家配送 2商家自提
    var saveCreateOrder = "1" //1保存 2保存并且配送
    private var isSelectDiscern = true //智能识别输入控制
    var address = "" //地址
    var lat = ""
    var lon = ""
    var provinceCode = ""
    var adCode = ""
    var cityName = ""
    var poiItem: PoiItem? = null
    var tenantId = ""
    var account = ""
    var name = ""
    var shopId: String? = ""
    var poiId: String? = ""
    var cityposition: Int = 0
    var shopidposition: Int = 0
    override fun initListener() {

        mDatabind.edtOrderCreateRemark.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                var input = s.toString()
                if (input.isEmpty()) {
                    mDatabind.txtCount.setText("${0}/200")
                } else {
//                    if (input.length == 201) {
//                        input = input.substring(0, s.toString().length - 1)
                    mDatabind.txtCount.setText("${input.length}/200")
//                    }

                }

            }
        })

        mDatabind.txtSelectStore.setSingleClickListener {
            if (shopBeanList.isNullOrEmpty()) {
                showToast("暂无门店数据")
                return@setSingleClickListener
            }
            var shopDialog =
                ShopDialog().newInstance(shopBeanList, "修改发货门店", cityposition, shopidposition)
            shopDialog.setOnresilience(object : ShopDialog.Onresilience {
                override fun resilience(
                    cityid: Int,
                    cityidname: String,
                    shopid: Int,
                    sho: Shop
                ) {
                    cityposition = cityid
                    shopidposition = shopid
                    poiId = sho.poiId
                    mDatabind.txtSelectStore.text = "${cityidname}/${sho.name}"
                    shopDialog.dismiss()
                }

                override fun Ondismiss() {
                }

            })
            shopDialog.show(supportFragmentManager)

        }
        mDatabind.txtDiscern.setSingleClickListener {
//            if (isSelectDiscern) {
            isSelectDiscern = false
            mDatabind.rlInputReceiveAddress.visibility = View.VISIBLE
            mDatabind.txtDiscern.visibility = View.GONE
//            }
//            else {
//                isSelectDiscern = true
//                mDatabind.rlInputReceiveAddress.visibility = View.GONE
//            }

        }
        mDatabind.txtDiscernInfo.setSingleClickListener {
            if (mDatabind.edtReceiveInfo.text.toString().isNullOrBlank()) {
                showToast("请输入收货信息")
                return@setSingleClickListener
            }
            mViewModel.addressParse(mDatabind.edtReceiveInfo.text.toString())
        }
        mDatabind.txtReceiveSelectAddress.setSingleClickListener {
            XXPermissions.with(this).permission(PermissionUtilis.Group.LOCAL)
                .request(object : OnPermissionCallback {
                    override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                        if (!allGranted) {
                            showToast("获取部分权限成功，但部分权限未正常授予")
                            return
                        }
                        //   startActivity(Intent(this@NewlyBuiltStoreActivity,NewOrderChangeAddressMapActivity::class.java))
                        // initStart()
                        ARouter.getInstance().build("/app/NewOrderChangeAddressMapActivity")
                            .withString("title", "收货地址")
                            .navigation(this@OrderCreateActivity, REQUEST_CODE)
                    }

                    override fun onDenied(
                        permissions: MutableList<String>,
                        doNotAskAgain: Boolean
                    ) {
                        if (doNotAskAgain) {
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(
                                this@OrderCreateActivity,
                                permissions
                            )
                        } else {
                            showToast("授权失败，请检查权限")
                        }
                    }
                })
        }
        mDatabind.txtAddGoods.setSingleClickListener {
            var orderCreate = OrderCreateGoodsDto(
                name = "默认商品",
                num = 1,
                salePrice = "1",
                totalPrice = "",
                goodsSpec = "默认规格",
                imgUrl = "https://static.igoodsale.com/default-goods.png"
            )
            orderGoodsAdapter.addData(orderCreate)
            KeyBoardUtil.openKeyBoard(this, mDatabind.txtAddGoods)
            orderGoodsAdapter.notifyDataSetChanged()
        }

        mDatabind.txtDeleteGoods.setSingleClickListener {
            val selectedItems = mutableListOf<OrderCreateGoodsDto>()
            var selectNumber = orderGoodsAdapter.data.count { it.isSelectGoods }
            for (goods in orderGoodsAdapter.data) {
                if (goods.isSelectGoods) {
                    selectedItems.add(goods)
                }
            }
            if (orderGoodsAdapter.data.size == selectedItems.size) {
                showToast("请至少保留一个商品")
                return@setSingleClickListener
            } else {
                for (index in selectedItems) {
                    orderGoodsAdapter.data.remove(index)
                }
            }

            for (item in orderGoodsAdapter.data) {
                item.isSelectGoods = false
                orderGoodsAdapter.notifyDataSetChanged()
            }
            mDatabind.txtDeleteGoods.visibility = View.GONE
            orderGoodsAdapter.data.forEach { it.isSelectGoods }
        }
        mDatabind.txtReceiveSelectTime.setSingleClickListener {
            showDatePickDialog(DateType.TYPE_YMDHM)//收货时间选择
        }

        mDatabind.txtReceiveWay.text = "商家配送"
        mDatabind.txtReceiveWay.setSingleClickListener {
            var arrayLiatDto = ArrayList<AccountItemSelect>()
            arrayLiatDto.add(AccountItemSelect("1", "商家配送"))
            arrayLiatDto.add(AccountItemSelect("2", "到店自提"))
            var accountSelectDialog = AccountSelectDialog().newInstance("收货方式", arrayLiatDto)
            accountSelectDialog.show(supportFragmentManager)
            accountSelectDialog.setOkClickItemLister { id, name ->
                selectAuthWay = id
                mDatabind.txtReceiveWay.text = name
                if (id == "1") {
                    mDatabind.btnSaveAndDis.visibility = View.VISIBLE
                    mDatabind.rlInputReceiveDetailAddress.visibility = View.VISIBLE
                    mDatabind.rlSelectReceiveAddress.visibility = View.VISIBLE
                    mDatabind.txtDiscern.visibility = View.VISIBLE
                    mDatabind.lineAddress.visibility = View.VISIBLE
                    mDatabind.lineAddressDetail.visibility = View.VISIBLE
                    if (!isSelectDiscern) {
                        mDatabind.txtDiscern.visibility = View.GONE
                        mDatabind.rlInputReceiveAddress.visibility = View.VISIBLE
                    }
                } else {
                    mDatabind.btnSaveAndDis.visibility = View.GONE
                    mDatabind.rlInputReceiveDetailAddress.visibility = View.GONE
                    mDatabind.rlSelectReceiveAddress.visibility = View.GONE
                    mDatabind.txtDiscern.visibility = View.GONE
                    mDatabind.lineAddress.visibility = View.GONE
                    mDatabind.lineAddressDetail.visibility = View.GONE
                    mDatabind.rlInputReceiveAddress.visibility = View.GONE
                }
            }
        }
        mDatabind.btnOkSave.setSingleClickListener {
            saveCreateOrder = "1"
            if (mDatabind.txtSelectStore.text.toString().trim().isNullOrBlank()) {
                showToast("请选择发货门店")
                return@setSingleClickListener
            }

            if (selectAuthWay == "1") {
                if (mDatabind.txtReceiveSelectAddress.text.toString().trim().isNullOrBlank()) {
                    showToast("请选择收货地址")
                    return@setSingleClickListener
                }
            }
            if (mDatabind.edtReceiveName.text.toString().trim().isNullOrBlank()) {
                showToast("请输入收货人姓名")
                return@setSingleClickListener
            }

            if (mDatabind.edtReceivePhone.text.toString().trim().isNullOrBlank()) {
                showToast("请输入收货人电话")
                return@setSingleClickListener
            }

            if (!isPhoneNumber(mDatabind.edtReceivePhone.text.toString().trim())) {
                showToast("请输入正确手机号")
                return@setSingleClickListener
            }

            if (mDatabind.txtReceiveSelectTime.text.toString().trim().isNullOrBlank()) {
                showToast("请选择收货时间")
                return@setSingleClickListener
            }
//            goodsArrayList.addAll(orderGoodsAdapter.data)
            for (item in orderGoodsAdapter.data) {
                goodsArrayList.add(
                    OrderCreateGoodsDto1(
                        name = item.name,
                        num = item.num,
                        salePrice = item.salePrice,
                        totalPrice = "${BigDecimal(item.salePrice).multiply(BigDecimal(item.num))}",
                        goodsSpec = item.goodsSpec,
                        imgUrl = item.imgUrl
                    )
                )
            }
            mViewModel.saveOrder(
                OrderCreateSaveDto(
                    arriveTime = mDatabind.txtReceiveSelectTime.text.toString(),
                    channelId = "11",
                    poiId = poiId,
                    lat = lat,
                    lon = lon,
                    deliveryType = selectAuthWay,
                    recvAddr = mDatabind.txtReceiveSelectAddress.text.toString() + "@@" + mDatabind.edtAddressDetail.text.toString(),
                    recvName = mDatabind.edtReceiveName.text.toString(),
                    recvPhone = mDatabind.edtReceivePhone.text.toString(),
                    remark = mDatabind.edtOrderCreateRemark.text.toString(),
                    selfGoodsDtoList = goodsArrayList
                )
            )
        }
        mDatabind.btnSaveAndDis.setSingleClickListener {
            saveCreateOrder = "2"
            if (mDatabind.txtSelectStore.text.toString().trim().isNullOrBlank()) {
                showToast("请选择发货门店")
                return@setSingleClickListener
            }

            if (selectAuthWay == "1") {
                if (mDatabind.txtReceiveSelectAddress.text.toString().trim().isNullOrBlank()) {
                    showToast("请选择收货地址")
                    return@setSingleClickListener
                }
            }
            if (mDatabind.edtReceiveName.text.toString().trim().isNullOrBlank()) {
                showToast("请输入收货人姓名")
                return@setSingleClickListener
            }

            if (mDatabind.edtReceivePhone.text.toString().trim().isNullOrBlank()) {
                showToast("请输入收货人电话")
                return@setSingleClickListener
            }

            if (!isPhoneNumber(mDatabind.edtReceivePhone.text.toString().trim())) {
                showToast("请输入正确手机号")
                return@setSingleClickListener
            }

            if (mDatabind.txtReceiveSelectTime.text.toString().trim().isNullOrBlank()) {
                showToast("请选择收货时间")
                return@setSingleClickListener
            }

//            goodsArrayList.addAll(orderGoodsAdapter.data)
            for (item in orderGoodsAdapter.data) {
                goodsArrayList.add(
                    OrderCreateGoodsDto1(
                        name = item.name,
                        num = item.num,
                        salePrice = item.salePrice,
                        totalPrice = "${BigDecimal(item.salePrice).multiply(BigDecimal(item.num))}",
                        goodsSpec = item.goodsSpec,
                        imgUrl = item.imgUrl
                    )
                )
            }
            mViewModel.saveOrder(
                OrderCreateSaveDto(
                    arriveTime = mDatabind.txtReceiveSelectTime.text.toString(),
                    channelId = "11",
                    poiId = poiId,
                    lat = lat,
                    lon = lon,
                    deliveryType = selectAuthWay,
                    recvAddr = mDatabind.txtReceiveSelectAddress.text.toString() + "@@" + mDatabind.edtAddressDetail.text.toString(),
                    recvName = mDatabind.edtReceiveName.text.toString(),
                    recvPhone = mDatabind.edtReceivePhone.text.toString(),
                    remark = mDatabind.edtOrderCreateRemark.text.toString(),
                    selfGoodsDtoList = goodsArrayList
                )
            )
        }
    }

    override fun createObserver() {
        mViewModel.cityPoiOfflineDto.onStart.observe(this) {
            showLoading("加载中")
        }
        mViewModel.cityPoiOfflineDto.onSuccess.observe(this) {
            disLoading()
            shopBeanList.addAll(it)
            if (shopBeanList.size == 1) {
                cityposition = 0
                shopidposition = 0
                poiId = shopBeanList[0].shopList!![0]!!.poiId
                mDatabind.txtSelectStore.text =
                    "${shopBeanList[0].name}/${shopBeanList[0].shopList!![0]!!.name}"
            }


        }
        mViewModel.cityPoiOfflineDto.onError.observe(this) {
            disLoading()
            showToast(it.msg)
        }

        mViewModel.saveCreateDto.onStart.observe(this) {
            showLoading("加载中")
        }
        mViewModel.saveCreateDto.onSuccess.observe(this) {
            disLoading()
            showToast("创建订单成功")
            if (selectAuthWay == "1" && saveCreateOrder == "2") {
                showToast("发起配送跳转")
                ARouter.getInstance().build("/app/OrderDisActivity")
                    .withSerializable("kk", it).navigation()
            }
            finish()
        }
        mViewModel.saveCreateDto.onError.observe(this) {
            disLoading()
            showToast(it.msg)
        }

        mViewModel.orderCreateAddressDiscern.onStart.observe(this) {
            showLoading("加载中")
        }
        mViewModel.orderCreateAddressDiscern.onSuccess.observe(this) {
            disLoading()
            lon = it.lng.toString()
            lat = it.lat.toString()
            address = it.province + it.city + it.county + it.town
            mDatabind.txtReceiveSelectAddress.text = address
            mDatabind.edtAddressDetail.setText("${it.detail}")
            mDatabind.edtReceivePhone.setText("${it.phonenum}")
            mDatabind.edtReceiveName.setText("${it.person}")
        }
        mViewModel.saveCreateDto.onError.observe(this) {
            disLoading()
            showToast(it.msg)
        }
    }


    //时间选择
    @SuppressLint("SetTextI18n")
    private fun showDatePickDialog(
        type: DateType,
    ) {
        val dialog = DatePickDialog(this)
        //设置上下年分限制
        dialog.setYearLimt(2)
        dialog.setStartDate(Date(System.currentTimeMillis()))
        //设置标题
        dialog.setTitle("选择时间")
        //设置类型
        dialog.setType(type)
        //设置消息体的显示格式，日期格式
        dialog.setMessageFormat("yyyy-MM-dd hh:mm")
        //设置选择回调
        dialog.setOnChangeLisener(null)
        //设置点击确定按钮回调
        dialog.setOnSureLisener { date ->
            // TODO: 时间校验
//            if (isSelectTimeCompare("${formatCurrentDateToString(date)}:00")){
//                mDatabind.txtOrderChangeTime.text = "${formatCurrentDateToString(date)}:00"
//            }else{
//                showToast("请选择正确时间")
//            }

            mDatabind.txtReceiveSelectTime.text = "${formatCurrentDateToString(date)}:00"
        }
        dialog.show()
    }

    private fun isPhoneNumber(input: String): Boolean {
        val regex = Regex("^1[3-9]\\d{9}$")
        return regex.matches(input)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // 处理返回的结果
            lon = data.getStringExtra("lon").toString()
            lat = data.getStringExtra("lat").toString()
            address = data.getStringExtra("address").toString()
            poiItem = data.getParcelableExtra("poiItem")
            provinceCode = poiItem?.provinceCode!!
            adCode = poiItem?.adCode!!
            cityName = poiItem?.cityName!!
            mDatabind.txtReceiveSelectAddress.text = address
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ACCESS_FINE_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 如果有权限跳转
                ARouter.getInstance().build("/app/OrderChangeAddressMapActivity")
                    .navigation(this, REQUEST_CODE)
            } else {
                showToast("您已经禁止权限，请手动开启")
                // 如果用户拒绝了权限，可以在这里处理相应的逻辑
            }
        }
    }
}